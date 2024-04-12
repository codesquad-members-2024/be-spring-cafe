package codesquad.springcafe.service;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public Article findBySequence(int sequence) {
        return articleRepository.findBySequence(sequence);
    }

    public List<Article> getArticles() {
        return articleRepository.getArticles();
    }
}
