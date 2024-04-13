package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.web.dto.ArticleCreateDto;
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
    public String quest(@ModelAttribute("article") ArticleCreateDto articleCreateDto) {
        articleService.saveArticle(articleCreateDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{sequence}")
    public String articleDetails(@PathVariable int sequence, Model model) {
        model.addAttribute("nlString", System.lineSeparator());
        model.addAttribute("article", articleService.findBySequence(sequence));
        return "/qna/show";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "/index";
    }
}