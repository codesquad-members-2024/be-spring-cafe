package codesquad.springcafe.article.service;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.user.domain.UserIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void addArticle(Article article) {
        articleRepository.add(article);
    }

    @Override
    public Article getArticle(String articleId) throws NoSuchArticleException {
        return articleRepository.get(articleId);
    }

    @Override
    public List<Article> getArticlesAsList() {
        return articleRepository.getArticles();
    }

    @Override
    public void updateArticle(Article article) throws NoSuchArticleException {
        articleRepository.update(article);
    }

    @Override
    public void deleteArticle(String articleId) throws NoSuchArticleException {
        articleRepository.delete(articleId);
    }

    @Override
    public boolean userHasPermission(UserIdentity userId, Article article) throws NoSuchArticleException{
        Article findArticle = articleRepository.get(article.getIdentifier());

        return userId.getUserId().contentEquals(findArticle.getWriter());
    }
}
