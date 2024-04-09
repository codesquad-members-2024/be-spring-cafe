package codesquad.springcafe.service.impl;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.repository.ArticleRepository;
import codesquad.springcafe.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleManagementService implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleManagementService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public long addArticle(Article article) {
        return articleRepository.addArticle(article);
    }

    @Override
    public Article findArticleById(int id) throws Exception {
        return articleRepository.findArticleById(id);
    }

    @Override
    public Article modifyArticle(Article article) throws Exception {
        return articleRepository.modifyArticle(article);
    }

    @Override
    public Article deleteArticle(Article article) {
        return articleRepository.deleteArticle(article);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    @Override
    public long increaseViewCount(Article article) {
        return articleRepository.increaseViewCount(article);
    }
}
