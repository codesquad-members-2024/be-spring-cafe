package codesquad.springcafe;

import codesquad.springcafe.database.ArticleDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @PostMapping("/questions")
    public String createQuestion(@ModelAttribute Article article) {
        ArticleDatabase.addArticle(article);
        logger.debug("add article : {}", article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        Article article = ArticleDatabase.getArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
