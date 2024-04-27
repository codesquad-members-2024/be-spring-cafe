package codesquad.springcafe.domain.article;

import codesquad.springcafe.web.dto.ArticleUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);
    void update(Long id, ArticleUpdateDto articleUpdateDto);
    Optional<Article> findById(Long id);
    List<Article> findAll();
}
