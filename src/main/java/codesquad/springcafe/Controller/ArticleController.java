package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.Article;
import codesquad.springcafe.Service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class ArticleController {

    private final ArticleService articleService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public String post(Article article) {
        // todo: 데이터에 저장하는 메서드 작성
        articleService.saveArticle(article);
        return "redirect:/index";
    }
}
