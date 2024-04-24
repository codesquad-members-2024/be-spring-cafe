package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.List;

public interface ArticleRepository {
    void createArticle(Article article);

    List<Article> findAllArticles();

    Article findById(long id);

    void updateViews(long id);

    void updateArticle(long id, ArticleDto articleDto);

    void deleteArticle(long id);
}