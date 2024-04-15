package codesquad.springcafe.controller;

import codesquad.springcafe.DB.H2Database;
import codesquad.springcafe.dto.ArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final H2Database h2Database;

    @Autowired
    public ArticleController(H2Database h2Database) {
        this.h2Database = h2Database;
    }

    @GetMapping("/qna")
    public String showForm() {
        return "qna/form";
    }

    @PostMapping("/qna")
    public String register(ArticleDto articleDto) {
        h2Database.addArticle(articleDto);
        return "redirect:/";
    }

    @GetMapping("/article/{articleId}")
    public String showArticle(Model model, @PathVariable("articleId") String articleId) {
        model.addAttribute("article", h2Database.getArticle(Long.parseLong(articleId)));
        return "qna/show";
    }
}