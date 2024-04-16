package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.service.ArticleService;
import model.article.Article;
import model.article.dto.ArticleCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public String postArticle(ArticleCreateDto articleCreateDto) {
        articleService.createArticle(articleCreateDto);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getUserProfile(@PathVariable int articleId, Model model) {
        Article article = articleService.findArticleById(articleId);

        model.addAttribute("article", article);

        return "article/show";
    }
}
