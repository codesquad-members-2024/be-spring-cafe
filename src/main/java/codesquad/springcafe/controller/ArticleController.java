package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/create")
    public String quest() {
        return "/qna/form";
    }

    @PostMapping("/articles/create")
    public String quest(@ModelAttribute Article article) {
        articleService.saveArticle(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleIndex}")
    public String articleDetails(@PathVariable Long articleIndex, Model model) {
        model.addAttribute("article", articleService.findByIndex(articleIndex));
        return "/qna/show";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "/index";
    }
}