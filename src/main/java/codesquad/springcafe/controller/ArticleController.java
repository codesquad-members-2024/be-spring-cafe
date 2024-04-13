package codesquad.springcafe.controller;

import codesquad.springcafe.repository.article.ArticleRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepositoryInterface articleRepository;

    @Autowired
    public ArticleController(ArticleRepositoryInterface articleRepository) {
        this.articleRepository = articleRepository;
    }

}
