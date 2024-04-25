package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.model.Article;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleDatabase articleDatabase;
    private final AtomicLong sequence = new AtomicLong();

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase){
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/add")
    public String getArticleCreationForm(){
        return "article/form";
    }

    @PostMapping("/add")
    public String createArticle(
            @ModelAttribute Article article
    ) {
        long seq = sequence.incrementAndGet();
        article.setSequence(seq);
        articleDatabase.addArticle(article);
        return "redirect:/";
    }


    @GetMapping("/detail/{sequence}")
    public String loadArticleContent(@PathVariable long sequence,
                                     Model model,
                                     HttpServletResponse response) throws IOException {
        Optional<Article> article = articleDatabase.findArticleBySequence(sequence);
        if(article.isEmpty()){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        model.addAttribute("article", article.get());
        return "article/show";
    }
}