package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.db.MemoryArticleDatabase;
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
    private final MemoryArticleDatabase memoryArticleDatabase;
    private final AtomicLong sequence = new AtomicLong();

    @Autowired
    public ArticleController(MemoryArticleDatabase memoryArticleDatabase){
        this.memoryArticleDatabase = memoryArticleDatabase;
    }

    @PostMapping("/articles")
    public String createPost(
            @RequestParam String title,
            @RequestParam String content
    ) {
        long seq = sequence.incrementAndGet();
        Article article = new Article(seq, title, content);
        memoryArticleDatabase.addArticle(article);
        return "redirect:/";
    }


    @GetMapping("/articles/{sequence}")
    public String loadArticleContent(@PathVariable long sequence, Model model){
        Optional<Article> article = memoryArticleDatabase.findArticleBySequence(sequence);
        if(article.isEmpty()){
            throw new NoSuchElementException();
        }
        model.addAttribute("article", article.get());
        return "article/show";
    }
}