package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);
    private static final String ARTICLEID = "ARTICLEID";
    private static final String USERID = "USERID";
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    private static final String CREATIONDATE = "CREATIONDATE";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createArticle(Article article) {
        String sql = "INSERT INTO ARTICLES (USERID, TITLE, CONTENT, CREATIONDATE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getUserId(), article.getTitle(), article.getContent(), article.getCreationDate().toString());
        logger.debug("Article Title : '{}' Updated At H2 Database", article.getTitle());
    }

    @Override
    public Optional<ArrayList<Article>> getAllArticles() {
        String sql = "SELECT ARTICLEID, USERID, TITLE, CONTENT, CREATIONDATE FROM ARTICLES";
        ArrayList<Article> articlePreviews = (ArrayList<Article>) jdbcTemplate.query(sql, new ArticleRowMapper());
        Collections.reverse(articlePreviews);
        return Optional.of(articlePreviews);
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        String sql = "SELECT USERID, TITLE, CONTENT, CREATIONDATE FROM ARTICLES WHERE ARTICLEID = ?";
        return jdbcTemplate.query(sql, new Object[]{articleId}, rs -> {
            if (rs.next()) {
                String userId = rs.getString(USERID);
                String title = rs.getString(TITLE);
                String content = rs.getString(CONTENT);
                String creationDate = rs.getString(CREATIONDATE);
                return Optional.of(new Article((long) articleId, userId, title, content, LocalDate.parse(creationDate)));
            }
            return Optional.empty();
        });
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            long articleId = rs.getLong(ARTICLEID);
            String userId = rs.getString(USERID);
            String title = rs.getString(TITLE);
            String content = rs.getString(CONTENT);
            String creationDate = rs.getString(CREATIONDATE);
            return new Article(articleId, userId, title, content, LocalDate.parse(creationDate));
        }
    }
}
