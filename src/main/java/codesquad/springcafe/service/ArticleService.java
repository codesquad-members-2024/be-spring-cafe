package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveQuestion(Article article) {
        articleRepository.save(article);
    }
}
