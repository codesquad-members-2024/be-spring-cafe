package codesquad.springcafe.service.article;

import codesquad.springcafe.domain.article.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article publish(Article article);

    Optional<Article> findArticle(long id);

    List<Article> findAllArticle();
}
