package codesquad.springcafe.controller;

import codesquad.springcafe.db.ArticleDatabase;
import codesquad.springcafe.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Optional;
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
        return "redirect:/";
    }


    @GetMapping("/articles/{sequence}")
    public String loadArticleContent(@PathVariable long sequence, Model model){
        Optional<Article> article = articleDatabase.findArticleBySequence(sequence);
        if(article.isEmpty()){
            throw new NoSuchElementException();
        }
        model.addAttribute("article", article.get());
        return "article/show";
    }
}