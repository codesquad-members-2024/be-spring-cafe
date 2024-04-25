package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
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
        List<ArticleProfileDto> articleProfiles = articleDatabase.findAllArticles();
        model.addAttribute("articles", articleProfiles);
        model.addAttribute("totalArticleNumber", articleDatabase.getTotalArticleNumber());
        return "article/list";
    }
}
