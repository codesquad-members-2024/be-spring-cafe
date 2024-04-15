package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping
    public String writeQuestion(@ModelAttribute ArticleRequestDto articleRequestDto) {
        Article article = new Article(articleRequestDto);
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Long articleId, Model model) {
        Article article = articleRepository.findById(articleId);
        log.debug("getArticle : {}", article);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
