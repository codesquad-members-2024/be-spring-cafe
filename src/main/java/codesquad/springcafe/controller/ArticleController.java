package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String qnaCreate(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String articleShow(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            model.addAttribute(article);
            return "qna/show";
        }
        return null;
    }
}