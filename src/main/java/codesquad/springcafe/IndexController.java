package codesquad.springcafe;

import codesquad.springcafe.database.ArticleDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model) {
        List<Article> articleList = ArticleDatabase.getReversedArticleList();
        model.addAttribute("articleList", articleList);
        return "index";
    }

}
