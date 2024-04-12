package codesquad.springcafe.article;

import codesquad.springcafe.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final ArticleRepository articleRepository;

    @Autowired
    public MainController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }
}
