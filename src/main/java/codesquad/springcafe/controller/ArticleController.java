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
        articleService.increaseViewCount(articleId);
        Article article = articleService.findArticleById(articleId);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        List<Comment> comments = commentService.findCommentsByAid(articleId);

        model.addAttribute("article", article);
        model.addAttribute("isWriter", sessionUser.getArticleIds().contains(articleId));
        model.addAttribute("writer", sessionUser.getUserId());
        model.addAttribute("commentList", comments);
        return "article/show";
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

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        UpdatedArticle updatedArticle = articleUpdateDto.createUpdatedArticle(sessionUser.getUserId());
        articleService.updateArticle(articleId, updatedArticle);
        return "redirect:/";
    }

    @DeleteMapping("/update/{articleId}")
    public String processDelete(@PathVariable long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/";
    }

    @PostMapping("/{articleId}/comments")
    public String processAnswerForm(@PathVariable long articleId, HttpSession httpSession,
                                    @Valid @ModelAttribute CommentWriteDto commentWriteDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/show";
        }

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        String userId = sessionUser.getUserId();
        Comment comment = commentWriteDto.createComment(articleId, userId);
        commentService.addComment(comment);
        return "redirect:/article/{articleId}";
    }

    @GetMapping("/invalid-modify")
    public String showInvalidModifyPage(Model model) {
        model.addAttribute("errorMsg", "다른 사람의 게시글은 수정할 수 없습니다.");
        return "error/form";
    }
}
