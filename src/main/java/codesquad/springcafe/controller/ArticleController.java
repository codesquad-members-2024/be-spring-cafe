package codesquad.springcafe.controller;

import codesquad.springcafe.repository.article.ArticleRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepositoryInterface articleRepository;

    @Autowired
    public ArticleController(ArticleRepositoryInterface articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/create")
    public String getArticleForm() {
        return "article/create";
    }

    @PostMapping("/create")
    public void postArticle() {
        //글쓰기 기능
    }

    @GetMapping("/{articleId}")
    public String showArticle() {
        return "article/show";
    }

}
