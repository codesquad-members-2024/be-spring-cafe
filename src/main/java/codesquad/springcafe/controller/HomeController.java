package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.model.Article;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String showArticleList(Model model, HttpServletRequest request){
        List<Article> articles = articleDatabase.findAllArticles();
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("springCafeMember") != null;

        model.addAttribute("articles", articles);
        model.addAttribute("totalArticleNumber", articleDatabase.getTotalArticleNumber());
        model.addAttribute("loginUser", isLoggedIn);
        return "article/list";
    }
}
