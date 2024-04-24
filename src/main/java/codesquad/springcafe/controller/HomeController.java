package codesquad.springcafe.controller;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.service.article.ArticleManager;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import codesquad.springcafe.util.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final ArticleManager articleManager;

    public HomeController(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "15") int size,
                                 Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.unSorted());
        Page<Article> articlePage = articleManager.findAllArticle(pageRequest);

        model.addAttribute("articlePage", articlePage);

        return "home";
    }
}
