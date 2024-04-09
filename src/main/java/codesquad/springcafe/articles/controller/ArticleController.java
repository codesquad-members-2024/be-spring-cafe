package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.service.ArticleService;
import model.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping
    public String showQnaForm() {
        return "/article/form";
    }

    @PostMapping
    public String postArticle(ArticleVO articleVO) {
        articleService.createArticle(articleVO);
        return "redirect:/";
    }

}
