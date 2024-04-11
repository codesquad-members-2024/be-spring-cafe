package codesquad.springcafe.controller;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.service.article.ArticleManager;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ArticleManager articleManager;

    public HomeController(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleManager.findAllArticle();
        model.addAttribute("articles", articles);

        return "home";
    }
}
