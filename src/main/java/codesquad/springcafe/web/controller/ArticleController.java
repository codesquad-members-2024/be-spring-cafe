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

    @GetMapping("/qua/index.html")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/questions")
    public String createArticle(@ModelAttribute Article article) {
        // 현재 시간 설정
        article.setCreateAt(LocalDateTime.now());

        // article 인덱스 부여
        article.setArticleNumber(articleRepository.articleSize() + 1);

        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleRepository.articlesAll();

        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/article/{articleNumber}")
    public String articleDetail(@PathVariable int articleNumber, Model model) {
        Optional<Article> article = articleRepository.findByIndex(articleNumber);
        article.ifPresent(a -> model.addAttribute("article", a));

        return "article/show";
    }
}
