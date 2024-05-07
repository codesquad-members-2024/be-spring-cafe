package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.util.PageRequest;
import java.util.ArrayList;
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
        return new ArrayList<>(articleRepository.getAll());
    }

    public List<Article> findAllByPaging(PageRequest pageRequest) {
        return new ArrayList<>(articleRepository.getAllByPaging(pageRequest));
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

    public Long getTotalCount() {
        return (long) findAll().size();
    }

    public Long getLastId() {
        return findAll().stream()
            .mapToLong(Article::getId).max().orElse(0L);
    }
}
