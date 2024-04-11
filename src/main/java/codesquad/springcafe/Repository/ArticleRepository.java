package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    public void save(Article article);
    public Optional<Article> findById(Long articleId) ;

    public List<Article> findAll();
}
