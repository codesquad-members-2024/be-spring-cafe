package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;
import codesquad.springcafe.articles.model.dto.ReplyViewDto;
import codesquad.springcafe.articles.service.ArticleService;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.exception.UserAccessException;
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

        HttpSession session = request.getSession();
        UserPreviewDto user = (UserPreviewDto) session.getAttribute("sessionedUser");
        String sessionedUserId = user.getUserId();

        ArrayList<ReplyViewDto> replies = articleService.getReplies(sessionedUserId, articleId);

        model.addAttribute("article", article);

        model.addAttribute("totalReplies", replies.size());

        model.addAttribute("replies", replies);

        return "article/show";
    }

    @GetMapping("/update/{articleId}")
    public String showArticleUpdatePage(@PathVariable long articleId, HttpServletRequest request, Model model) {
        Article article = articleService.findArticleById(articleId);

        HttpSession session = request.getSession();
        UserPreviewDto userPreviewDto = (UserPreviewDto) session.getAttribute("sessionedUser");

        if (!userPreviewDto.getUserId().equals(article.getUserId())) {
            throw new UserAccessException("게시물에 대한 접근 권한이 없습니다.");
        }
        model.addAttribute("article", article);
        return "article/updateForm";
    }

    @PutMapping("/update/{articleId}")
    public String updateArticle(@PathVariable long articleId, ArticleUpdateDto articleUpdateDto) {
        articleService.updateArticle(articleId, articleUpdateDto);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/delete/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpServletRequest request) {
        Article article = articleService.findArticleById(articleId);

        HttpSession session = request.getSession();
        UserPreviewDto userPreviewDto = (UserPreviewDto) session.getAttribute("sessionedUser");

        if (!userPreviewDto.getUserId().equals(article.getUserId())) {
            throw new UserAccessException("게시물에 대한 접근 권한이 없습니다.");
        }

        articleService.deleteArticle(articleId);
        return "redirect:/";
    }

    @PostMapping("/{articleId}/answers")
    public String createReply(@PathVariable long articleId, ReplyCreationRequest replyCreationRequest) {
        articleService.createReply(articleId, replyCreationRequest);

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/{articleId}/answers/{replyId}")
    public String deleteReply(@PathVariable long articleId, @PathVariable long replyId) {
        articleService.deleteReply(replyId);

        return "redirect:/articles/{articleId}";
    }
}
