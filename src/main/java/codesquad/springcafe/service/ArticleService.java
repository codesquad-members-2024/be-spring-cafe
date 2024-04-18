package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService() {
        this.articleRepository = new ArticleRepository();
    }

    public void writeArticle(Article article) {
        articleRepository.addArticle(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleByIndex(int index) {
        return articleRepository.findByIndex(index);
    }
}
