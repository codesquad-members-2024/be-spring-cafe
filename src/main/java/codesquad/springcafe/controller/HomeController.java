package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ArticleDatabase articleDatabase;

    public HomeController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 홈 화면에서 글 목록을 조회할 수 있습니다.
     */
    @GetMapping
    public String home(Model model) {
        model.addAttribute("articles", articleDatabase.findAll());
        return "home/index";
    }
}
