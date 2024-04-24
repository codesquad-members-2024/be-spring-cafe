package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showPage() {
        return "qna/form";
    }

    @PostMapping("/new")
    public String create(ArticleForm form) {
        articleService.save(form);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String show(@PathVariable("articleId") Long articleId, Model model) {
        // model에 글 넣음
        Optional<Article> article = articleService.findOne(articleId);
        model.addAttribute("articles", article.orElseThrow(() -> new RuntimeException("Article not found")));
        return "qna/show";
    }
}
