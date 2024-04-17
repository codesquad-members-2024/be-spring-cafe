package codesquad.springcafe.controller;

import codesquad.springcafe.dto.RegisterArticle;
import codesquad.springcafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna")
    public String showForm() {
        return "qna/form";
    }

    @PostMapping("/qna")
    public String register(RegisterArticle registerArticle) {
        articleService.registerArticle(registerArticle);
        return "redirect:/";
    }

    @GetMapping("/article/{articleId}")
    public String showArticle(Model model, @PathVariable("articleId") String articleId) {
        model.addAttribute("article", articleService.getArticleDetail(articleId));
        return "qna/show";
    }
}