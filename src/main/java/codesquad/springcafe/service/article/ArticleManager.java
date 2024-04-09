package codesquad.springcafe.service.article;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleManager implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleManager(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article publish(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findArticle(long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepository.findAll();
    }
}
