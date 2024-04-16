package codesquad.springcafe.article;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/create")
    public String createArticle(Model model, @ModelAttribute Article article) {
        articleRepository.add(article);

        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showArticle(Model model, @PathVariable String articleId) {
        Article article;
        try {
            article = articleRepository.getArticle(articleId);
        } catch (NoSuchArticleException noArticle) {
            return "redirect:/article/no_article.html";
        }

        model.addAttribute("article", article);

        return "article/show";
    }
}
