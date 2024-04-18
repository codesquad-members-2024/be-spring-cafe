package codesquad.springcafe;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleDatabase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    ArticleDatabase articleDatabase;

    @Autowired
    public IndexController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping({"/", "/index", "/index.html"})
    public String index(HttpServletRequest request, Model model) {
        List<Article> articleList = articleDatabase.getReversedArticleList();
        model.addAttribute("articleList", articleList);
        return "index";
    }
}
