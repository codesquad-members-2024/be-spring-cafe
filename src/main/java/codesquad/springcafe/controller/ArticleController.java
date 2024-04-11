package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/qna/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @PostMapping("/qna/form")
    public String qnaCreate(@ModelAttribute Article article) {
        articleRepository.add(article);
        return "redirect:/";
    }
}