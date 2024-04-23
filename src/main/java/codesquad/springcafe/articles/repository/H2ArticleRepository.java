package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;
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
import java.util.List;
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
    private static final String PAGEVIEWS = "PAGEVIEWS";
    private static final String COMMENT = "COMMENT";
    private static final String REPLYID = "REPLYID";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ArticleRepository(JdbcTemplate jdbcTemplate) {
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
                String creationDate = rs.getString(CREATIONDATE);
                long pageViews = rs.getLong(PAGEVIEWS);

                Article article = new Article(userId, title, content);
                article.setArticleId(articleId);
                article.setCreationDate(LocalDate.parse(creationDate));
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
    }

    @Override
    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        String sql = "UPDATE ARTICLES SET TITLE = ?, CONTENT = ? WHERE ARTICLEID = ?";
        jdbcTemplate.update(sql, articleUpdateDto.getTitle(), articleUpdateDto.getContent(), articleId);
    }

    @Override
    public void deleteArticle(long articleId) {
        String articleDeleteSql = "UPDATE ARTICLES SET DELETED = TRUE WHERE ARTICLEID = ?";

        jdbcTemplate.update(articleDeleteSql, articleId);

        String replyDeleteSql = "UPDATE REPLIES SET DELETED = TRUE WHERE ARTICLEID = ?";

        jdbcTemplate.update(replyDeleteSql, articleId);
    }


    @Override
    public void createReply(Reply reply) {
        String sql = "INSERT INTO REPLIES (ARTICLEID, USERID, COMMENT, CREATIONDATE, DELETED) VALUES (?, ?, ?, ?, false)";

        jdbcTemplate.update(sql, reply.getArticleId(), reply.getUserId(), reply.getComment(), reply.getCreationDate().toString());

        logger.debug("Reply Comment : '{}' Created At H2 Database", reply.getComment());
    }

    @Override
    public Optional<ArrayList<Reply>> getReplies(long articleId) {
        String sql = "SELECT REPLYID, ARTICLEID, USERID, COMMENT, CREATIONDATE FROM REPLIES WHERE ARTICLEID = ? AND DELETED = FALSE";
        Object[] params = new Object[]{articleId};

        ArrayList<Reply> replies = (ArrayList<Reply>) jdbcTemplate.query(sql, params, new ReplyRowMapper());
        return Optional.of(replies);
    }

    @Override
    public void deleteReply(long replyId) {
        String sql = "UPDATE REPLIES SET DELETED = TRUE WHERE REPLYID = ?";
        jdbcTemplate.update(sql, replyId);
    }

    @Override
    public Optional<Reply> findReplyById(long replyId) {
        String sql = "SELECT REPLYID, ARTICLEID, USERID, COMMENT, CREATIONDATE FROM REPLIES WHERE REPLYID = ? AND DELETED = FALSE";
        Object[] params = new Object[]{replyId};

        List<Reply> replies = jdbcTemplate.query(sql, params, new ReplyRowMapper());
        return Optional.of(replies.get(0));
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            long articleId = rs.getLong(ARTICLEID);
            String userId = rs.getString(USERID);
            String title = rs.getString(TITLE);
            String content = rs.getString(CONTENT);
            String creationDate = rs.getString(CREATIONDATE);
            long pageViews = rs.getLong(PAGEVIEWS);

            Article article = new Article(userId, title, content);
            article.setArticleId(articleId);
            article.setCreationDate(LocalDate.parse(creationDate));
            article.setPageViews(pageViews);
            return article;
        }
    }

    private static class ReplyRowMapper implements RowMapper<Reply> {
        @Override
        public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
            long replyId = rs.getLong(REPLYID);
            long articleId = rs.getLong(ARTICLEID);
            String userId = rs.getString(USERID);
            String comment = rs.getString(COMMENT);
            String creationDate = rs.getString(CREATIONDATE);

            Reply reply = new Reply(articleId, userId, comment);

            reply.setReplyId(replyId);
            reply.setCreationDate(LocalDate.parse(creationDate));

            return reply;
        }
    }

}
