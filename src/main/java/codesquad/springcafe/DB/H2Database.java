package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2Database {
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleRowMapper = (resultSet, rowNum) -> {
        Article article = new Article(
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("time").toLocalDateTime(),
                resultSet.getLong("id")
        );
        return article;
    };

    @Autowired
    public H2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO USERS (userId, name, email, password) VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public List<Map<String, Object>> getAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM USERS");
    }

    public void addArticle(ArticleDto articleDto) {
        jdbcTemplate.update("INSERT INTO Articles (writer, title, contents, time) VALUES (?,?,?,?)",
                articleDto.getWriter(), articleDto.getTitle(), articleDto.getContents(), LocalDateTime.now());
    }

    public List<Article> getAllArticles() {
        return jdbcTemplate.query("SELECT * FROM Articles", articleRowMapper);
    }

    public Article getArticle(long id) {
        final String SELECT_ARTICLE = "SELECT * FROM Articles WHERE id= ?";
        return jdbcTemplate.queryForObject(SELECT_ARTICLE, articleRowMapper, id);
    }
}