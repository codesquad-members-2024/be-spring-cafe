package codesquad.springcafe.article.service;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

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
        return articleRepository.getArticle(articleId);
    }

    @Override
    public List<Article> getArticlesAsList() {
        return articleRepository.getArticles();
    }
}
