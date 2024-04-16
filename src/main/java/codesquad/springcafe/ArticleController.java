package codesquad.springcafe;

import codesquad.springcafe.db.ArticleDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ArticleController {
    private final ArticleDatabase articleDatabase;
    private final AtomicLong sequence = new AtomicLong();

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase){
        this.articleDatabase = articleDatabase;
    }

    @PostMapping("/articles")
    public String createPost(
            @RequestParam String title,
            @RequestParam String content
    ) {
        long seq = sequence.incrementAndGet();
        Article article = new Article(seq, title, content);
        articleDatabase.addArticle(article);
        return "redirect:/articles";
    }
}