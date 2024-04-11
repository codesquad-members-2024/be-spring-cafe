package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.Article;
import codesquad.springcafe.Service.ArticleService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    private final ArticleService articleService;

    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Article> articles = articleService.findAllArticle();
        model.addAttribute("articles", articles);
        return "index";
    }
}
