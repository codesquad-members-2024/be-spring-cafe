package codesquad.springcafe.service;

import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.ListArticle;
import codesquad.springcafe.model.UpdatedArticle;
import java.util.List;

public interface ArticleService {
    void addArticle(Article article) throws DuplicateUserIdException;

    Article findArticleById(long id);

    void updateArticle(long id, UpdatedArticle article);

    void deleteArticle(long id);

    List<ListArticle> findAllArticle();

    void increaseViewCount(long id);

    List<Long> findUserArticleIds(String userId);
}
