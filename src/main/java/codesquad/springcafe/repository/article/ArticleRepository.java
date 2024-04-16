package codesquad.springcafe.repository.article;

import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.UpdatedArticle;
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
