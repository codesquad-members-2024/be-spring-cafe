package codesquad.springcafe.article.dao;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleDao;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
public class ArticleH2Dao implements ArticleDao {

    private final JdbcTemplate jdbcTemplate;

    public ArticleH2Dao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // PK는 INT타입으로 AUTO_INCREMENT 를 사용하여 DB가 관리하도록 설정.
    @Override
    public void save(Article article) {
        final String sql = "INSERT INTO ARTICLE (writer, title, contents, createAt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents(), article.getCreateAt());
    }

    @Override
    public Collection<Article> findAll() {
        final String sql = "SELECT id, writer, title, contents, createAt FROM ARTICLE";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public Optional<Article> findBy(int id) {
        final String sql = "SELECT id, writer, title, contents, createAt FROM ARTICLE WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, articleRowMapper(), id));
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            LocalDateTime createAt = rs.getTimestamp("createAt").toLocalDateTime();
            Article article = new Article(writer, title, contents, createAt);
            article.setId(id);
            return article;
        };
    }

}
