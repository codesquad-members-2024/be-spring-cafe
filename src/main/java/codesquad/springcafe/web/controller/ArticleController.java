package codesquad.springcafe.web.controller;

import codesquad.springcafe.web.domain.Article;
import codesquad.springcafe.web.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/questions")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/questions")
    public String createArticle(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleRepository.articlesAll();

        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/article/{articleId}")
    public String articleDetail(@PathVariable Long articleId, Model model) {
        Optional<Article> article = articleRepository.findByIndex(articleId);
        article.ifPresent(a -> model.addAttribute("article", a));

        return "article/show";
    }
}
