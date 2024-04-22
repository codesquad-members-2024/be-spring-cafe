package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.db.ArticleDatabase;
import codesquad.springcafe.articles.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    private final ArticleDatabase articleDatabase;
    @Autowired
    public MemoryArticleRepository(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @Override
    public void createArticle(Article article) {
        articleDatabase.addArticle(article);
    }

    @Override
    public Optional<ArrayList<Article>> getAllArticles() {
        return Optional.of(articleDatabase.getAllArticles());
    }

    @Override
    public Optional<Article> findArticleById(long articleId) {
        return Optional.ofNullable(articleDatabase.findArticleById(articleId));
    }

    @Override
    public void incrementPageView(long articleId) {
        articleDatabase.updatePageView(articleId);
    }

    @Override
    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        articleDatabase.updateArticle(articleId, articleUpdateDto);
    }

    @Override
    public void deleteArticle(long articleId) {
        articleDatabase.deleteArticle(articleId);
    }


    @Override
    public void createReply(Reply reply) {
        articleDatabase.addReply(reply);
    }

    @Override
    public Optional<ArrayList<Reply>> getReplies(long articleId) {
        return Optional.of(articleDatabase.getReplies(articleId));
    }
}


