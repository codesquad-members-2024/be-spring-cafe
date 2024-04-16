package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleWriteDto;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/write")
    public String showWritePage() {
        return "article/form";
    }

    @PostMapping("/write")
    public String processWriteForm(@ModelAttribute ArticleWriteDto articleWriteDto, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser"); // 로그인해야지 접근할 수 있으므로
        String userId = sessionUser.getUserId();
        Article article = articleWriteDto.createArticle(userId);
        articleService.addArticle(article);
        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showDetailPage(Model model, @PathVariable long articleId) {
        articleService.increaseViewCount(articleId);
        Optional<Article> article = articleService.findArticleById(articleId);

        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "article/show";
        }
        return "error/not_found";
    }
}
