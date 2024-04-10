package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/questions/create")
    public String createQuestion() {
        return "/qna/form";
    }

    @PostMapping("/questions/create")
    public String quest(@ModelAttribute Article article) {
        articleService.saveQuestion(article);
        return "redirect:/";
    }
}
