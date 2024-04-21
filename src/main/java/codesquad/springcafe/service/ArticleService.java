package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import codesquad.springcafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(Article article) {
        articleRepository.addArticle(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).get();
    }
}
