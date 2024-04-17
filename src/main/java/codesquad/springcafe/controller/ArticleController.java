package codesquad.springcafe.controller;

import codesquad.springcafe.repository.ArticleRepository;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.RegisterArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleRepository h2ArticleRepository;

    @Autowired
    public ArticleController(ArticleRepository h2ArticleRepository) {
        this.h2ArticleRepository = h2ArticleRepository;
    }

    @GetMapping("/qna")
    public String showForm() {
        return "qna/form";
    }

    @PostMapping("/qna")
    public String register(RegisterArticle registerArticle) {
        Article article = new Article(
                registerArticle.getWriter(), registerArticle.getTitle(),
                registerArticle.getContents(), registerArticle.getTime()
        );
        h2ArticleRepository.add(article);
        return "redirect:/";
    }

    @GetMapping("/article/{articleId}")
    public String showArticle(Model model, @PathVariable("articleId") String articleId) {
        model.addAttribute("article", h2ArticleRepository.getById(Long.parseLong(articleId)));
        return "qna/show";
    }
}