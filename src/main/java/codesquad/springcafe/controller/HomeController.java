package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.repository.article.ArticleRepositoryInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ArticleRepositoryInterface articleRepository;

    @Autowired
    public HomeController(ArticleRepositoryInterface articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String home(Model model) {
        List<Article> articles = articleRepository.findAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }
}
