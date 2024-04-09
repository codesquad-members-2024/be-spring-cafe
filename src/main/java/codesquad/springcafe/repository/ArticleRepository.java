package codesquad.springcafe.repository;

import codesquad.springcafe.dto.Article;
import java.util.List;

public interface ArticleRepository {
    int addArticle(Article article);

    Article findArticleById(int id) throws Exception;

    Article modifyArticle(Article article) throws Exception;

    Article deleteArticle(Article article);

    List<Article> findAllArticle();

    int increaseViewCount(Article article);
}
