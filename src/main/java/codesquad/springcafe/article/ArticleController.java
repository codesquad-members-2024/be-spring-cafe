package codesquad.springcafe.article;

import codesquad.springcafe.article.database.ArticleDatabase;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ArticleController.class);
    private final ArticleDatabase articleDatabase;

    public ArticleController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/form")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/form")
    public String createArticle(ArticleCreateDto articleCreateDto, HttpServletRequest request) {
        articleDatabase.save(articleCreateDto.toEntity());
        log.info(request.getSession().getAttribute("nickname") + " created article");
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        model.addAttribute("article", articleDatabase.findById(articleId));
        return "article/show";
    }
}
