package codesquad.springcafe.main.controller;


import codesquad.springcafe.articles.service.ArticleService;
import model.article.dto.ArticlePreviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/main")
public class MainController {
    private final ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showMain(Model model) {
        ArrayList<ArticlePreviewDto> articlePreviews = articleService.getAllArticles();

        model.addAttribute("articlePreviews", articlePreviews);
        model.addAttribute("totalArticles", articlePreviews.size());

        return "main/index";
    }
}
