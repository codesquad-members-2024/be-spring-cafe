package codesquad.springcafe.article;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.article.service.ArticleService;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public String createArticle(Model model, @ModelAttribute Article article) {
        articleService.addArticle(article);

        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showArticle(Model model, @PathVariable String articleId) throws NoSuchArticleException{
        Article article = articleService.getArticle(articleId);

        model.addAttribute("article", article);

        return "article/show";
    }
}
