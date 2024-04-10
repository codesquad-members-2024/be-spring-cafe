package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.repository.ArticleRepository;
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

    public List<Article> getArticleList() {
        return articleRepository.getArticles();
    }
}
