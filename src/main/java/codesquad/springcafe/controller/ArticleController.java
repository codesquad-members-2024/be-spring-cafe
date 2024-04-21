package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
public class ArticleController {

    public static final String LOGIN_USER_ID = "loginUserId";

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping
    public String writeQuestion(@ModelAttribute ArticleRequestDto articleRequestDto, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        Article article = new Article(userId, articleRequestDto);
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Long articleId, Model model) {
        Article article = articleRepository.findById(articleId);
        log.debug("getArticle : {}", article);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
