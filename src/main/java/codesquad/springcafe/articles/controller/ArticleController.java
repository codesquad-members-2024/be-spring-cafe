package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.repository.ArticleRepository;
import model.Article;
import model.ArticleData;
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

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public String postArticle(ArticleData articleData) {
        articleRepository.createArticle(articleData);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getUserProfile(@PathVariable int articleId, Model model) {
        Article article = articleRepository.findArticleById(articleId);
        model.addAttribute("article", article);

        return "/article/show";
    }
}
