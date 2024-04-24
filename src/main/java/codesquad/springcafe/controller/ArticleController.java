package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.ArticleNotFoundException;
import codesquad.springcafe.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/create")
    public String getArticleForm() {
        return "article/create";
    }

    @PostMapping("/create")
    public String postArticle(@ModelAttribute ArticleDto articleDto) {
        articleRepository.createArticle(articleDto);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showArticle(@PathVariable long id, Model model) {
        articleRepository.increaseViews(id);

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("해당 게시글이 존재하지 않습니다."));

        logger.debug("게시글 상세: {}", article.toDto());
        model.addAttribute("article", article);

        return "article/show";
    }
}