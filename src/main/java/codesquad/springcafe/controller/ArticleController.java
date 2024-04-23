package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleUpdateDto;
import codesquad.springcafe.dto.ArticleWriteDto;
import codesquad.springcafe.dto.CommentWriteDto;
import codesquad.springcafe.exception.service.DuplicateArticleIdException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedArticle;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/write")
    public String showWritePage(@ModelAttribute("articleWriteDto") ArticleWriteDto articleWriteDto) {
        return "article/form";
    }

    @PostMapping("/write")
    public String processWriteForm(@Valid @ModelAttribute ArticleWriteDto articleWriteDto, BindingResult bindingResult,
                                   HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }

        try {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser"); // 로그인해야지 접근할 수 있으므로
            String userId = sessionUser.getUserId();
            Article article = articleWriteDto.createArticle(userId);
            articleService.addArticle(article);
            sessionUser.addArticleId(article.getId()); // 세션 유저에 작성한 게시글 Id 추가
            return "redirect:/";
        } catch (DuplicateArticleIdException e) {
            bindingResult.reject("errorAid");
            return "article/form";
        }
    }

    @GetMapping("/{articleId}")
    public String showDetailPage(@PathVariable long articleId, Model model, HttpSession httpSession,
                                 @ModelAttribute("commentWriteDto") CommentWriteDto commentWriteDto) {
        canIncreaseViewCount(httpSession, articleId);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        String userId = sessionUser.getUserId();
        Article article = articleService.findArticleById(articleId);
        List<Comment> comments = commentService.findCommentsByAid(articleId);
        boolean canDelete = comments.stream().allMatch(comment -> comment.getUserId().equals(userId));

        model.addAttribute("article", article);
        model.addAttribute("isWriter", sessionUser.getArticleIds().contains(articleId));
        model.addAttribute("canDelete", canDelete);
        model.addAttribute("writer", sessionUser.getUserId());
        model.addAttribute("commentList", comments);

        Object commentBlankError = httpSession.getAttribute("commentBlankError");
        if (commentBlankError != null && (boolean) commentBlankError) {
            model.addAttribute("IsCommentBlank", true);
        }
        httpSession.removeAttribute("commentBlankError");
        httpSession.removeAttribute("commentControlFlag");
        return "article/show_comment";
    }

    private void canIncreaseViewCount(HttpSession httpSession, long articleId) {
        Object commentBlankError = httpSession.getAttribute("commentBlankError");
        Object commentAddFlag = httpSession.getAttribute("commentControlFlag");

        if (commentBlankError == null && commentAddFlag == null) {
            articleService.increaseViewCount(articleId);
        }
    }

    @GetMapping("/update/{articleId}")
    public String showUpdatePage(@PathVariable Long articleId,
                                 @ModelAttribute("articleUpdateDto") ArticleUpdateDto articleUpdateDto) {
        Article article = articleService.findArticleById(articleId);
        articleUpdateDto.setTitle(article.getTitle());
        articleUpdateDto.setContent(article.getContent());
        return "article/update";
    }

    @PutMapping("/update/{articleId}")
    public String processUpdateForm(@PathVariable long articleId, HttpSession httpSession,
                                    @Valid @ModelAttribute ArticleUpdateDto articleUpdateDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/update";
        }

        UpdatedArticle updatedArticle = articleUpdateDto.createUpdatedArticle();
        articleService.updateArticle(articleId, updatedArticle);
        httpSession.setAttribute("commentControlFlag", true);
        return "redirect:/article/{articleId}";
    }

    @DeleteMapping("/update/{articleId}")
    public String processDelete(@PathVariable long articleId, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        String userId = sessionUser.getUserId();
        List<Comment> otherComments = commentService.findCommentsByAid(articleId)
                .stream().filter(comment -> !comment.getUserId().equals(userId)).toList();

        if (otherComments.isEmpty()) { // 다른 사용자의 댓글이 없는 경우만 게시글 삭제가 가능하다.
            commentService.deleteArticlesComment(articleId);
            articleService.deleteArticle(articleId);
            return "redirect:/";
        }
        return "redirect:/article/invalid-delete";
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable long commentId, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        String sessionUserId = sessionUser.getUserId();
        Comment comment = commentService.findCommentsById(commentId);
        if (!comment.getUserId().equals(sessionUserId)) {
            return "redirect:/article/invalid-modify";
        }
        httpSession.setAttribute("commentControlFlag", true);
        commentService.deleteComment(commentId);
        return "redirect:/article/{articleId}";
    }

    @GetMapping("/invalid-modify")
    public String showInvalidModifyPage(Model model) {
        model.addAttribute("errorMsg", "다른 사용자의 게시글과 댓글은 수정할 수 없습니다.");
        return "error/form";
    }

    @GetMapping("/invalid-delete")
    public String showInvalidDeletePage(Model model) {
        model.addAttribute("errorMsg", "댓글이 없는 경우만 삭제가 가능합니다.");
        return "error/form";
    }
}
