package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ArticleService articleService;
    private final CommentService commentService;

    public HomeController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    /**
     * 홈 화면에서 글 목록을 조회할 수 있습니다.
     */
    @GetMapping
    public String home(Model model) {

        List<Article> articles = articleService.getArticles();

        Map<Long, Long> commentCounts = commentService.getCommentCounts();

        model.addAttribute("articles", articles);
        model.addAttribute("commentCounts", commentCounts);

        return "home/index";
    }
}
