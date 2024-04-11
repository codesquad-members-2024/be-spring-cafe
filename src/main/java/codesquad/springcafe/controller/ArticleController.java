package codesquad.springcafe.controller;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.service.ArticleService;
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
    public String writeForm() {
        return "article/form";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Article article) {
        Article addArticle = articleService.addArticle(article);
        logger.info("[게시글 생성 완료] - " + addArticle);

        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showForm(Model model, @PathVariable long articleId) throws Exception {
        long updatedArticleId = articleService.increaseViewCount(articleId);
        logger.info("[" + updatedArticleId + "번째 게시글 조회수 증가]");

        Article article = articleService.findArticleById(articleId);
        logger.info("[" + articleId + "번째 게시글 가져오기 성공] - " + article);

        model.addAttribute("article", article);
        return "article/show";
    }
}
