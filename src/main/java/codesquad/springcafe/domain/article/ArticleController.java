package codesquad.springcafe.domain.article;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.article.service.ArticleService;
import codesquad.springcafe.constants.Constant;
import codesquad.springcafe.domain.comment.service.CommentService;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.exceptions.NotAuthenticationException;
import codesquad.springcafe.domain.user.dto.UserIdentity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/create")
    public String showArticleForm() {
        return "article/form";
    }

    @PostMapping("/create")
    public String createArticle(Model model, @ModelAttribute Article article) {
        articleService.addArticle(article);

        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showArticle(Model model, @PathVariable String articleId) throws NoSuchArticleException {
        Article article = articleService.getArticle(articleId);

        //조회수 추가
        article.addViewCount();
        articleService.updateArticle(article);

        model.addAttribute("article", article);

        model.addAttribute("comments", commentService.getComments(article));

        return "article/show";
    }

    @GetMapping("/update/{articleId}")
    public String showUpdateArticleForm(@PathVariable String articleId, Model model, HttpSession session) {
        Article nowArticle = articleService.getArticle(articleId);
        if (!articleService.userHasPermission((UserIdentity) session.getAttribute(Constant.SESSION_LOGIN), nowArticle)) {
            throw new NotAuthenticationException();
        }
        model.addAttribute("articleId", articleId);

        return "article/updateForm";
    }

    @PostMapping("/update/{articleId}")
    public String updateArticle(@PathVariable String articleId, @ModelAttribute Article article, HttpSession session) throws NoSuchArticleException, NotAuthenticationException {
        article.setIdentifierFromString(articleId);
        articleService.updateArticle(article);

        return "redirect:/article/show/" + articleId;
    }

    @PostMapping("/delete")
    public String deleteArticle(@ModelAttribute Article article, HttpSession session) throws NoSuchArticleException, NotAuthenticationException{
        if (!articleService.userHasPermission((UserIdentity) session.getAttribute(Constant.SESSION_LOGIN), article)) {
            throw new NotAuthenticationException();
        }

        String identifier = article.getIdentifier();

        articleService.deleteArticle(identifier);

        return "redirect:/";
    }
}
