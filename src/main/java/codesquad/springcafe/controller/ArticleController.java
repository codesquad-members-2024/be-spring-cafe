package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public String createArticle(@ModelAttribute Article article, Model model) {
        Article newArticle = articleService.createArticle(article.getWriter(), article.getTitle(), article.getContents());
        model.addAttribute("article", newArticle);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showArticleList(Model model) {
        List<Article> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("articles/{index}")
    public String showArticle(@PathVariable Long index,
        Model model) {
        Article article = articleService.findArticleByIndex(index);
        model.addAttribute("article", article);
        return "/qna/show";
    }
}
