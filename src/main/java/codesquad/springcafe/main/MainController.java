package codesquad.springcafe.main;

import codesquad.springcafe.article.ArticleDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    ArticleDatabase articleDatabase;

    public MainController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("articles", articleDatabase.findAll());
        return "index";
    }

}
