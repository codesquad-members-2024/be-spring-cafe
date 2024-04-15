package springcafe.article.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springcafe.article.model.Article;
import springcafe.article.service.ArticleService;

import java.util.Map;

@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String articleList(Model model) {
        Map<Long, Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);

        return "index";

    }

    @GetMapping("qna/show/{articleId}")
    public String readArticle(@PathVariable Long articleId, Model model) {

        Article article = articleService.findById(articleId);
        model.addAttribute("nlString", System.lineSeparator());
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("qna/questions")
    public String questionCreate(Article article) {

        return "qna/form";
    }


    @PostMapping("qna/questions")
    public String questionCreate(@Valid Article article, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            return "qna/form";

        }
        this.articleService.create(article.getWriter(), article.getTitle(), article.getContents());
        return "redirect:/";
    }
}
