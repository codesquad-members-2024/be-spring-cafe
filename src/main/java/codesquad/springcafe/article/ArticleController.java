package codesquad.springcafe.article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/article/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        model.addAttribute("article", articleDatabase.findById(articleId));
        return "article/show";
    }
}
