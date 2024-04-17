package codesquad.springcafe.domain.article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);
    Article findById(Long id);
    List<Article> findAll();
}
