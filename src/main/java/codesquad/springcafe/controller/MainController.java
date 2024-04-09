package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.repository.ArticleRepository;
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
        articleRepository.add(new Article("테스터" , " 테스트용 제목", "테스트용 내용"));

        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }
}
