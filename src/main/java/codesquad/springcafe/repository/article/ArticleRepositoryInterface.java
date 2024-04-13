package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryInterface {
    Article createArticle(Article article);

    List<Article> findAllArticles();

    Optional<Article> findByArticleId(Long articleId);
}
