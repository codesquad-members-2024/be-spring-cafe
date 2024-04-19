package codesquad.springcafe.database.article;

import codesquad.springcafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("H2ArticleDatabase")
public class H2ArticleDatabase implements ArticleDatabase {

    private static final Logger logger = LoggerFactory.getLogger(H2ArticleDatabase.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ArticleDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // 스프링의 JDBC 추상화 계층
    }

    // RowMapper
    private RowMapper<Article> articleRowMapper(){
        return (rs, rowNum) -> new Article(
                rs.getInt("id"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("time").toLocalDateTime(),
                rs.getInt("views"));
    }

    @Override
    public void saveArticle(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns(  "id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("time", Timestamp.valueOf(article.getTime()));
        parameters.put("views", article.getViews());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public List<Article> getAllArticles() {
        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    @Override
    public Article getArticleById(int id) {
        List<Article> articleList = jdbcTemplate.query("select * from articles where id = ?", articleRowMapper(), id);
        return articleList.get(0);
    }

    @Override
    public int getArticleSize() {
        Integer size = jdbcTemplate.queryForObject("select count(*) from articles", Integer.class);
        return size != null ? size : 0;
    }

    @Override
    public boolean isArticleEmpty() {
        return (getArticleSize() == 0);
    }

    @Override
    public void incrementViewsById(int id) {
        int rowsAffected = jdbcTemplate.update("UPDATE articles SET VIEWS = VIEWS + 1 WHERE ID = ?", id);

        if (rowsAffected == 0) {
            logger.debug("해당 ID에 해당하는 article을 찾을 수 없습니다. ID: {}", id);
        } else {
            logger.debug("ID {}에 대한 views가 성공적으로 증가되었습니다.", id);
        }
    }
}
