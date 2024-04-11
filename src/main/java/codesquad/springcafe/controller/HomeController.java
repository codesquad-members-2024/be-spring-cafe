package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleRepository articleRepository;

    public HomeController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Article> articles = articleRepository.findAllArticle();
        model.addAttribute("articles", articles);
        return "index";
    }
}
