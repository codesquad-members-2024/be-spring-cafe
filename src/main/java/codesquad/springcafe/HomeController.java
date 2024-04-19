package codesquad.springcafe;

import codesquad.springcafe.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ArticleRepository articleRepository;

    @Autowired
    public HomeController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.getArticles());

        return "index";
    }
}
