package codesquad.springcafe.controller;

import codesquad.springcafe.DB.Database;
import codesquad.springcafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping("/qna")
    public String showForm() {
        return "qna/form";
    }

    @PostMapping("/qna")
    public String register(Article article) {
        Database.addArticle(article);
        article.setId(Database.articlesSize());
        logger.debug("new article: " + article);
        return "redirect:/";
    }

    @GetMapping("/article/{articleId}")
    public String showArticle(Model model, @PathVariable("articleId") String articleId) {
        model.addAttribute("article", Database.getArticle(Integer.parseInt(articleId)));
        return "qna/show";
    }
}