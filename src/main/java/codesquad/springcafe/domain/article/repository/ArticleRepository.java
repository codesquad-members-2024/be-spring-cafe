package codesquad.springcafe.domain.article.repository;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;

import java.util.List;

public interface ArticleRepository {

    void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException;

    Article findById(int id);
    List<Article> findAll();
    List<Article> findByUserId(String userId);
    void addPoint(int articleId);
    void update(int id, ArticlePostReq articlePostReq);
    void delete(int id);

    void deleteAll();

    List<Article> getArticles(int page);
}
