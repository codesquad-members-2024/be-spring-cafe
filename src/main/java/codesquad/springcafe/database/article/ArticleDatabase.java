package codesquad.springcafe.database.article;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleDatabase {
    Article add(Article article);

    Optional<Article> findBy(Long id);

    List<Article> findAll(String nickname);

    List<Article> findAll();

    List<Article> findPageArticles(Long offset, int articlesPerPage);

    void update(Article article);

    void increaseViews(Long id);

    void softDelete(Long id);
//    void deleteArticle(Long id);

    void clear();
    
    Long countTotalArticles();

    String findWriter(Long id);
}
