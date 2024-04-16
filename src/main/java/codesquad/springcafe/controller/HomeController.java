package codesquad.springcafe.controller;

import codesquad.springcafe.db.ArticleDatabase;
import codesquad.springcafe.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleDatabase articleDatabase;

    @Autowired
    public HomeController(ArticleDatabase articleDatabase){
        this.articleDatabase = articleDatabase;
    }

    @GetMapping(value = {"/", "/index.html"})
    public String showArticleList(Model model){
        List<Article> articles = articleDatabase.findAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("totalArticleNumber", articleDatabase.getTotalArticleNumber());
        return "article/list";
    }
}
