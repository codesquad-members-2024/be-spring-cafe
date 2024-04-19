package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.comment.CommentDatabase;
import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ArticleDatabase articleDatabase;
    private final CommentDatabase commentDatabase;

    public HomeController(ArticleDatabase articleDatabase, CommentDatabase commentDatabase) {
        this.articleDatabase = articleDatabase;
        this.commentDatabase = commentDatabase;
    }

    /**
     * 홈 화면에서 글 목록을 조회할 수 있습니다.
     */
    @GetMapping
    public String home(Model model) {

        List<Article> articles = articleDatabase.findAll();
        model.addAttribute("articles", articles);

        Map<Long, Long> commentCounts = articles.stream()
                .collect(Collectors.toMap(
                        Article::getId, // 'Article' 객체 자체를 키로 사용
                        article -> (long) commentDatabase.findAll(article.getId()).size()
                        // 'findAll'로 댓글 리스트를 가져와서 그 크기를 값으로 사용
                ));

        model.addAttribute("commentCounts", commentCounts);

        return "home/index";
    }
}
