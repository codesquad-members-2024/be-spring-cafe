package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;
import codesquad.springcafe.articles.model.dto.ReplyViewDto;
import codesquad.springcafe.articles.service.ArticleService;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.exception.ArticleAccessException;
import codesquad.springcafe.exception.ReplyAccessException;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public String postArticle(ArticleCreationRequest articleCreationRequest) {
        articleService.createArticle(articleCreationRequest);

        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, HttpServletRequest request, Model model) {
        articleService.incrementPageViews(articleId);

        Article article = articleService.findArticleById(articleId);

        String sessionedUserId = ((UserPreviewDto) request.getSession().getAttribute("sessionedUser")).getUserId();

        ArrayList<ReplyViewDto> replies = articleService.getReplies(sessionedUserId, articleId);

        model.addAttribute("article", article);
        model.addAttribute("totalReplies", replies.size());
        model.addAttribute("replies", replies);

        return "article/show";
    }

    @GetMapping("/update/{articleId}")
    public String showArticleUpdatePage(@PathVariable long articleId, HttpServletRequest request, Model model) {
        validateArticleAuth(request, articleId);

        Article article = articleService.findArticleById(articleId);

        model.addAttribute("article", article);
        return "article/updateForm";
    }

    @PutMapping("/update/{articleId}")
    public String updateArticle(@PathVariable long articleId, HttpServletRequest request, ArticleUpdateDto articleUpdateDto) {
        validateArticleAuth(request, articleId);

        articleService.updateArticle(articleId, articleUpdateDto);

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/delete/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpServletRequest request, Model model) {
        validateArticleAuth(request, articleId);

        articleService.deleteArticle(articleId);

        return "redirect:/";
    }

    @PostMapping("/{articleId}/answers")
    public String createReply(@PathVariable long articleId, ReplyCreationRequest replyCreationRequest) {
        articleService.createReply(articleId, replyCreationRequest);

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/{articleId}/answers/{replyId}")
    public String deleteReply(@PathVariable long replyId, HttpServletRequest request) {
        validateReplyAuth(request, replyId);

        articleService.deleteReply(replyId);

        return "redirect:/articles/{articleId}";
    }

    private void validateArticleAuth(HttpServletRequest request, long articleId) {
        HttpSession session = request.getSession();
        UserPreviewDto userPreviewDto = (UserPreviewDto) session.getAttribute("sessionedUser");
        String sessionedUserId = userPreviewDto.getUserId();

        String ownerId = articleService.findArticleById(articleId).getUserId();

        if (!sessionedUserId.equals(ownerId)) {
            throw new ArticleAccessException("게시글에 접근할 수 있는 권한이 없습니다.");
        }
    }

    private void validateReplyAuth(HttpServletRequest request, long replyId) {
        HttpSession session = request.getSession();
        UserPreviewDto userPreviewDto = (UserPreviewDto) session.getAttribute("sessionedUser");
        String sessionedUserId = userPreviewDto.getUserId();

        String ownerId = articleService.findReplyById(replyId).getUserId();

        if (!sessionedUserId.equals(ownerId)) {
            throw new ReplyAccessException("댓글에 접근할 수 있는 권한이 없습니다.");
        }
    }
}
