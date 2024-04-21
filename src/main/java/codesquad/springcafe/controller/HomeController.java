package codesquad.springcafe.controller;

import codesquad.springcafe.domain.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ArticleRepository articleRepository;

    @Autowired
    public HomeController(ArticleRepository h2ArticleRepository) {
        this.articleRepository = h2ArticleRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.getAll());
        return "index";
    }
}