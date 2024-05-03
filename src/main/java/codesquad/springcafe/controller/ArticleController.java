package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static codesquad.springcafe.controller.UserController.LOGIN_USER_ID;

@Controller
@RequestMapping("/question")
public class ArticleController {

    public static final String ARTICLE_ID = "articleId";

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping
    public String writeQuestion(@ModelAttribute ArticleRequestDto articleRequestDto, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        Article article = new Article(userId, articleRequestDto);
        articleService.createArticle(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Long articleId, Model model) {
        log.debug("getArticle : {}", articleId);
        Article article = articleService.findById(articleId);
        List<Reply> replies = replyService.findRepliesByArticle(articleId);
        model.addAttribute("article", article);
        model.addAttribute("replies", replies);
        return "qna/show";
    }

    @GetMapping("/update/{articleId}")
    public String getArticleUpdateForm(@PathVariable Long articleId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        articleService.validateArticleWriter(articleId, userId);

        model.addAttribute(ARTICLE_ID, articleId);
        return "qna/update_form";
    }

    @PutMapping("/{articleId}")
    public String updateArticle(@PathVariable Long articleId, @ModelAttribute ArticleRequestDto articleRequestDto, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        articleService.validateArticleWriter(articleId, userId);
        articleService.update(articleId, articleRequestDto);
        return "redirect:/question/" + articleId;
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable Long articleId, HttpSession session){
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        articleService.validateArticleWriter(articleId, userId);

        log.debug("게시물 삭제 성공 {}", userId);
        articleService.delete(articleId);
        return "redirect:/";
    }
}
