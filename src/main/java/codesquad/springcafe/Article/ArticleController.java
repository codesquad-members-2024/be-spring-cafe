package codesquad.springcafe.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/article/form")
    public String article() {
        logger.info("게시글 작성 폼 요청");
        return "article/form";
    }

    @PostMapping("/questions")
    public String articleCreate(@ModelAttribute Article article) {
        article.setTime(LocalDateTime.now());
        article.setArticleNum(articleRepository.articleSize() + 1);
        articleRepository.add(article);
        logger.info("새 게시글 추가: {}", article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        logger.info("게시글 목록 조회");
        return "index";
    }

    @GetMapping("/article/{articleNumber}")
    public String articleDetail(@PathVariable int articleNumber, Model model) {
        Optional<Article> article = articleRepository.findByIndex(articleNumber);
        article.ifPresent(a -> {
            model.addAttribute("article", a);
            logger.info("게시글 상세 조회: {}", articleNumber);
        });
        return "article/show";
    }
}
