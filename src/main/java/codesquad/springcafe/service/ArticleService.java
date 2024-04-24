package codesquad.springcafe.service;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.exception.ArticleNotFountException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public Article findById(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            return optionalArticle.get();
        }
        throw new ArticleNotFountException();
    }

    public boolean checkArticleWriter(Long articleId, String userId) {
        Article article = findById(articleId);
        return article.checkWriter(userId);
    }

    public void update(Long articleId, ArticleRequestDto articleRequestDto) {
        articleRepository.update(articleId, articleRequestDto);
    }

    public void delete(Long articleId) {
        articleRepository.delete(articleId);
    }
}
