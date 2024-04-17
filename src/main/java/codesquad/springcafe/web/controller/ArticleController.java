package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import codesquad.springcafe.web.validation.ArticleCreateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleCreateValidator articleCreateValidator;

    public ArticleController(ArticleService articleService, ArticleCreateValidator articleCreateValidator) {
        this.articleService = articleService;
        this.articleCreateValidator = articleCreateValidator;
    }

    @InitBinder("create")
    public void initTargetCreate(WebDataBinder dataBinder) {
        dataBinder.addValidators(articleCreateValidator);
    }

    @GetMapping("/articles/create")
    public String quest(Model model) {
        model.addAttribute("create", new ArticleCreateDto());
        return "qna/form";
    }

    @PostMapping("/articles/create")
    public String quest(@Validated @ModelAttribute("create") ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/form";
        }
        articleService.saveArticle(articleCreateDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{sequence}")
    public String articleDetails(@PathVariable int sequence, Model model) {
        model.addAttribute("nlString", System.lineSeparator());
        model.addAttribute("article", articleService.findBySequence(sequence));
        return "qna/show";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "index";
    }
}