package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long createArticle(ArticleDto articleDto);

    List<Article> findAllArticles();

    Optional<Article> findArticleById(Long id);

    void clear();
}
