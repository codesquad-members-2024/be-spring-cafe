package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/qna/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @PostMapping("/qna/form")
    public String qnaCreate(@ModelAttribute Article article) {
        articleRepository.add(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleId}")
    public String articleShow(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            model.addAttribute(article);
            return "/qna/show";
        }
        return null;
    }
}