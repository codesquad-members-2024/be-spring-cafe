package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void upload(Article newArticle) {
        articleRepository.save(newArticle);
    }

    public List<Article> findAll() {
        return articleRepository.getAll();
    }

    public Article findById(Long id) {
        Optional<Article> targetArticle = articleRepository.getById(id);
        return targetArticle.orElse(null);
    }

    public void update(Article modifiedArticle) {
        articleRepository.modify(modifiedArticle);
    }

    public void delete(Long id) {
        articleRepository.removeSoft(id);
    }
}
