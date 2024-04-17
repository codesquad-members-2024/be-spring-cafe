package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ArticleRepository articleRepository;

    public HomeController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/index.html")
    public String home(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute(articles.reversed());
        return "/index";
    }
}
