package codesquad.springcafe.service.impl;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.dto.UpdatedArticle;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ArticleManagementService implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleManagementService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepository.addArticle(article);
    }

    @Override
    public Article findArticleById(long id) throws Exception {
        return articleRepository.findArticleById(id);
    }

    @Override
    public long modifyArticle(long id, UpdatedArticle article) throws Exception {
        return articleRepository.modifyArticle(id, article);
    }

    @Override
    public long deleteArticle(long id) {
        return articleRepository.deleteArticle(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    @Override
    public long increaseViewCount(long id) throws Exception {
        return articleRepository.increaseViewCount(id);
    }
}
