package codesquad.springcafe;

import codesquad.springcafe.database.ArticleDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    ArticleDatabase articleDatabase;

    @Autowired
    public IndexController(ArticleDatabase articleH2Database) {
        this.articleDatabase = articleH2Database;
    }

    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model) {
        List<Article> articleList = articleDatabase.getReversedArticleList();
        model.addAttribute("articleList", articleList);
        return "index";
    }

}
