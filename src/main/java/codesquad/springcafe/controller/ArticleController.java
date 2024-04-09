package codesquad.springcafe.controller;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.service.ArticleService;
import java.util.Date;
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
        article.setCreationTime(new Date());
        int id = (int) articleService.addArticle(article);
        logger.info("[" + id + "번째 게시글 생성 완료] - " + article);

        return "redirect:/";
    }

    @GetMapping("/show/{articleId}")
    public String showForm(Model model, @PathVariable int articleId) throws Exception {
        Article article = articleService.findArticleById(articleId - 1);
        logger.info("[" + articleId + "번째 게시글 가져오기 성공] - " + article.toString());

        long viewCount = articleService.increaseViewCount(article);
        logger.info("[" + articleId + "번째 게시글 조회수 : " + viewCount);

        model.addAttribute("article", article);
        return "article/show";
    }
}
