package codesquad.springcafe.article;

import codesquad.springcafe.user.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    private final UserDatabase userDatabase;

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase, UserDatabase userDatabase) {
        this.articleDatabase = articleDatabase;
        this.userDatabase = userDatabase;
    }

    @PostMapping("/questions")
    public String createQuestion(@ModelAttribute Article article) {
        if (!userDatabase.isExistUser(article.getWriter())) {
            return "redirect:/qna/form";
        }
        articleDatabase.addArticle(article);
        logger.debug("add article : {}", article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        Article article = articleDatabase.getArticle(articleId);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
