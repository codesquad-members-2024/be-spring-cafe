package codesquad.springcafe.articles.service;

import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.repository.ArticleRepository;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(ArticleCreationRequest articleCreationRequest) {
        Article article = new Article(articleCreationRequest.getUserId(), articleCreationRequest.getTitle(), articleCreationRequest.getContent());
        logger.debug("Article Created : {}", article);
        articleRepository.createArticle(article);
    }

    public ArrayList<Article> getAllArticles() {
        Optional<ArrayList<Article>> articles = articleRepository.getAllArticles();
        return articles.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Article findArticleById(long articleId) {
        Optional<Article> articleContent = articleRepository.findArticleById(articleId);
        return articleContent.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public void incrementPageViews(long articleId) {
        articleRepository.incrementPageView(articleId);
    }

    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        articleRepository.updateArticle(articleId, articleUpdateDto);
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteArticle(articleId);
    }
}
