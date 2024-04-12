package codesquad.springcafe.repository.article;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.dto.UpdatedArticle;
import codesquad.springcafe.exception.db.ArticleNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article addArticle(Article article);

    Optional<Article> findArticleById(long id) throws ArticleNotFoundException;

    long modifyArticle(long id, UpdatedArticle article) throws ArticleNotFoundException;

    long deleteArticle(long id);

    List<Article> findAllArticle();

    long increaseViewCount(long id) throws ArticleNotFoundException;
}
