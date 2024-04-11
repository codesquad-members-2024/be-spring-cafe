package codesquad.springcafe.main.controller;


import codesquad.springcafe.articles.repository.ArticleRepository;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("/main")
public class MainController {
    private final ArticleRepository articleRepository;
    @Autowired
    public MainController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String showMain(Model model) {
        ArrayList<Article> articles = articleRepository.getAllArticles();

        model.addAttribute("articles", articles);
        model.addAttribute("totalArticles", articles.size());

        return "/main/index";
    }
}
