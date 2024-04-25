package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.model.User;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO `article` (id, timestamp, writer, title, content) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            article.getId(), Timestamp.valueOf(article.getTimeStamp()), article.getWriter(), article.getTitle(), article.getContent());
    }

    @Override
    public List<Article> getAll() {
        String sql = "SELECT * FROM `article` WHERE deleted = FALSE";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("id"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getBoolean("deleted")
            );
            return article;
        });
    }

    @Override
    public List<Reply> getRepliesById(Long id) {
        String sql = "SELECT reply.articleId, reply.index, reply.writer, reply.timestamp, reply.content "
            + "FROM `article` LEFT JOIN `reply` WHERE article.id = ? AND article.deleted = FALSE AND article.id = reply.articleId";
        return jdbcTemplate.query(sql, new Object[]{id}, (resultSet, rowNum) -> {
            Reply reply = new Reply(
                resultSet.getLong("articleId"),
                resultSet.getLong("index"),
                resultSet.getString("writer"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("content")
            );
            return reply;
        });
    }

    @Override
    public Optional<Article> getById(Long id) {
        String sql = "SELECT * FROM `article` WHERE id = ? AND deleted = FALSE";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("id"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getBoolean("deleted")
            );
            return Optional.ofNullable(article);
        });
    }

    @Override
    public void modify(Article modifiedArticle) {
        String sql = "UPDATE `article` SET title = ?, content = ? WHERE id = ? AND deleted = FALSE";
        jdbcTemplate.update(sql,
            modifiedArticle.getTitle(), modifiedArticle.getContent(), modifiedArticle.getId());
    }

    @Override
    public void removeHard(Long id) {
        String sql = "DELETE FROM `article` WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void removeSoft(Long id) {
        String sql = "UPDATE `article` SET deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}