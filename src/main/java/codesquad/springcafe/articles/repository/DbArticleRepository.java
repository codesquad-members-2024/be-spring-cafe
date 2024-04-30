package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Primary
@Repository
public class DbArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(DbArticleRepository.class);
    private static final String ARTICLEID = "ARTICLEID";
    private static final String USERID = "USERID";
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    private static final String CREATIONDATE = "CREATIONDATE";
    private static final String PAGEVIEWS = "PAGEVIEWS";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createArticle(Article article) {
        String sql = "INSERT INTO ARTICLES (USERID, TITLE, CONTENT, CREATIONDATE, PAGEVIEWS, DELETED) VALUES (?, ?, ?, ?, ?, false)";
        jdbcTemplate.update(sql, article.getUserId(), article.getTitle(), article.getContent(), article.getCreationDate().toString(), article.getPageViews());
        logger.debug("Article Title : '{}' Created At H2 Database", article.getTitle());
    }

    @Override
    public Optional<ArrayList<Article>> getAllArticles() {
        String sql = "SELECT ARTICLEID, USERID, TITLE, CONTENT, CREATIONDATE, PAGEVIEWS FROM ARTICLES WHERE DELETED = FALSE";
        ArrayList<Article> articles = (ArrayList<Article>) jdbcTemplate.query(sql, new ArticleRowMapper());
        Collections.reverse(articles);
        return Optional.of(articles);
    }

    @Override
    public Optional<Article> findArticleById(long articleId) {
        String sql = "SELECT USERID, TITLE, CONTENT, CREATIONDATE, PAGEVIEWS FROM ARTICLES WHERE ARTICLEID = ?";
        return jdbcTemplate.query(sql, new Object[]{articleId}, rs -> {
            if (rs.next()) {
                String userId = rs.getString(USERID);
                String title = rs.getString(TITLE);
                String content = rs.getString(CONTENT);
                LocalDateTime creationDate = rs.getTimestamp(CREATIONDATE).toLocalDateTime();
                long pageViews = rs.getLong(PAGEVIEWS);

                Article article = new Article(userId, title, content);
                article.setArticleId(articleId);
                article.setCreationDate(creationDate);
                article.setPageViews(pageViews);
                return Optional.of(article);
            }
            return Optional.empty();
        });
    }


    @Override
    public void incrementPageView(long articleId) {
        String sql = "UPDATE ARTICLES SET PAGEVIEWS = PAGEVIEWS + 1 WHERE ARTICLEID = ?";
        jdbcTemplate.update(sql, articleId);
        logger.debug("Article ID : {} Page View Increased", articleId);
    }

    @Override
    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        String sql = "UPDATE ARTICLES SET TITLE = ?, CONTENT = ? WHERE ARTICLEID = ?";
        jdbcTemplate.update(sql, articleUpdateDto.getTitle(), articleUpdateDto.getContent(), articleId);
        logger.debug("Article ID : {} Updated", articleId);
    }

    @Override
    public void deleteArticle(long articleId) {
        String articleDeleteSql = "UPDATE ARTICLES SET DELETED = TRUE WHERE ARTICLEID = ?";

        jdbcTemplate.update(articleDeleteSql, articleId);

        String replyDeleteSql = "UPDATE REPLIES SET DELETED = TRUE WHERE ARTICLEID = ?";

        jdbcTemplate.update(replyDeleteSql, articleId);

        logger.debug("Article ID : {} Deleted", articleId);
    }


    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            long articleId = rs.getLong(ARTICLEID);
            String userId = rs.getString(USERID);
            String title = rs.getString(TITLE);
            String content = rs.getString(CONTENT);
            LocalDateTime creationDate = rs.getTimestamp(CREATIONDATE).toLocalDateTime();
            long pageViews = rs.getLong(PAGEVIEWS);

            Article article = new Article(userId, title, content);
            article.setArticleId(articleId);
            article.setCreationDate(creationDate);
            article.setPageViews(pageViews);
            return article;
        }
    }
}
