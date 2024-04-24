package codesquad.springcafe.domain.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class APIArticleController {

    private final ArticleService articleService;

    @Autowired
    public APIArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("")
    public List<Article> get(@RequestParam int page){
        return articleService.getArticlesAtPage(page);
    }
}
