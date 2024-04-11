package codesquad.springcafe.repository.article;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.dto.UpdatedArticle;
import java.util.List;

public interface ArticleRepository {
    Article addArticle(Article article);

    Article findArticleById(long id) throws Exception;

    long modifyArticle(long id, UpdatedArticle article) throws Exception;

    long deleteArticle(long id);

    List<Article> findAllArticle();

    long increaseViewCount(long id) throws Exception;
}
