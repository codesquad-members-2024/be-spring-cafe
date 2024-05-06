package codesquad.springcafe.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Article> articleRowMapper = (rs, rowNum) -> {
        Article article = new Article(
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents")
        );
        article.setArticleNum(rs.getInt("articleNum"));
        article.setTime(rs.getTimestamp("time").toLocalDateTime());
        return article;
    };

    @Override
    public Article addArticle(Article article) {
        String sql = "INSERT INTO Articles (writer, title, contents, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents(), article.getTime());
        return article;
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM Articles", articleRowMapper);
    }

    @Override
    public Optional<Article> findByIndex(int number) {
        List<Article> articles = jdbcTemplate.query("SELECT * FROM Articles WHERE articleNum = ?", articleRowMapper, number);
        return articles.stream().findAny();
    }

    @Override
    public int articleSize() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Articles", Integer.class);
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM Articles");
    }
}
