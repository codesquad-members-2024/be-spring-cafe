package codesquad.springcafe.service;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

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
        return articleRepository.findById(articleId);
    }

    public boolean checkArticleWriter(Long articleId, String userId) {
        Article article = articleRepository.findById(articleId);
        return article.checkWriter(userId);
    }

    public void update(Long articleId, ArticleRequestDto articleRequestDto) {
        articleRepository.update(articleId, articleRequestDto);
    }
}
