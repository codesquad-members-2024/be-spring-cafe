package codesquad.springcafe.articles.repository;

import model.article.Article;
import model.article.dto.ArticleContentDto;
import model.article.dto.ArticlePreviewDto;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleRepository {
    void createArticle(Article article);

    Optional<ArrayList<ArticlePreviewDto>> getAllArticles();

    Optional<ArticleContentDto> findArticleById(int articleId);
}
