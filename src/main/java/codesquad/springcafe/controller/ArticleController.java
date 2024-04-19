package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            logger.debug("게시글 상세: {}", article.toDto());
            article.increaseViews(); // 조회수 증가
            model.addAttribute("article", article);
            return "article/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글 조회 실패");
        }
    }
}