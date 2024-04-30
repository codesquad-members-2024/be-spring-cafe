package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import codesquad.springcafe.web.PageGroup;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String home(@RequestParam(defaultValue = "1") Long page, Model model) {

        Long totalArticleSize = articleService.getSearchedCount();
        List<Article> articles = articleService.getArticlesByPage(page);
        Map<Long, Long> commentCounts = commentService.getCommentCounts(articles);
        PageGroup pageGroup = getPageGroup(page, totalArticleSize);

        model.addAttribute("totalArticleSize", totalArticleSize);
        model.addAttribute("articles", articles);
        model.addAttribute("commentCounts", commentCounts);
        model.addAttribute("pageGroup", pageGroup);
        return "home/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, @RequestParam(defaultValue = "1") Long page, Model model) {
        Long totalArticleSize = articleService.getSearchedCount(keyword);
        List<Article> articles = articleService.getSearchedArticlesByPage(keyword, page);
        Map<Long, Long> commentCounts = commentService.getCommentCounts(articles);
        PageGroup pageGroup = getPageGroup(page, totalArticleSize);

        model.addAttribute("keyword", keyword);
        model.addAttribute("totalArticleSize", totalArticleSize);
        model.addAttribute("articles", articles);
        model.addAttribute("commentCounts", commentCounts);
        model.addAttribute("pageGroup", pageGroup);
        return "home/search";
    }

    private PageGroup getPageGroup(Long page, Long totalArticleSize) {
        int totalPages = articleService.getTotalPages(totalArticleSize);
        return new PageGroup(page, totalPages);
    }
}
