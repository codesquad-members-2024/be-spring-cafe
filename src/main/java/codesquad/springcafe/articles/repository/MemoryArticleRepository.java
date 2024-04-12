package codesquad.springcafe.articles.repository;

import db.ArticleDatabase;
import model.article.Article;
import model.article.dto.ArticleContentDto;
import model.article.dto.ArticlePreviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    @Override
    public void createArticle(Article article) {
        ArticleDatabase.addArticle(article);
    }

    @Override
    public Optional<ArrayList<ArticlePreviewDto>> getAllArticles() {
        ArrayList<Article> articles = ArticleDatabase.getAllArticles();
        ArrayList<ArticlePreviewDto> articlePreviews = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ArticlePreviewDto articlePreviewDto = new ArticlePreviewDto((long) (i + 1), article.getUserId(), article.getTitle(), article.getContent());
            articlePreviews.add(articlePreviewDto);
        }
        return Optional.of(articlePreviews);
    }

    @Override
    public Optional<ArticleContentDto> findArticleById(int articleId) {
        Article article = ArticleDatabase.findArticleById(articleId);
        if (article == null) {
            return Optional.empty();
        }
        ArticleContentDto articleContent = new ArticleContentDto(article.getUserId(), article.getTitle(), article.getContent(), article.getCreationDate().toString());
        return Optional.of(articleContent);
    }
}
