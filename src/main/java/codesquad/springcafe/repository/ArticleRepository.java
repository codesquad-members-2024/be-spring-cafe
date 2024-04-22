package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Long save(Article article);

    Optional<Article> findById(Long articleId);

    List<Article> findAllArticle();

    void clear();

    void update(Long articleId, ArticleRequestDto articleRequestDto);

    void delete(Long articleId);
}
