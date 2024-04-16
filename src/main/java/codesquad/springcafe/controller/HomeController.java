package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.db.MemoryArticleDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final MemoryArticleDatabase memoryArticleDatabase;

    @Autowired
    public HomeController(MemoryArticleDatabase memoryArticleDatabase){
        this.memoryArticleDatabase = memoryArticleDatabase;
    }

    @GetMapping(value = {"/", "/index.html"})
    public String showArticleList(Model model){
        List<Article> articles = memoryArticleDatabase.findAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("totalArticleNumber", memoryArticleDatabase.getTotalArticleNumber());
        return "article/list";
    }
}
