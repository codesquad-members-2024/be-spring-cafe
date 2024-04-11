package codesquad.springcafe.article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    ArticleDatabase articleDatabase;

    public ArticleController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }


    @GetMapping("/article/form")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/article/create")
    public String createArticle(ArticleCreateDto articleCreateDto) {
        articleDatabase.saveArticle(articleCreateDto.toEntity());
        return "redirect:/";
    }
}
