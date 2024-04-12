package codesquad.springcafe.article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    ArticleDatabase articleDatabase;

    public ArticleController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/form")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/create")
    public String createArticle(ArticleCreateDto articleCreateDto) {
        articleDatabase.save(articleCreateDto.toEntity());
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        model.addAttribute("article", articleDatabase.findById(articleId));
        return "article/show";
    }
}
