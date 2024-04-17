package codesquad.springcafe.db;

import codesquad.springcafe.model.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class H2ArticleDatabase implements ArticleDatabase {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ArticleDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("articles")
                .usingColumns("sequence", "writer", "title", "content", "publishTime");
    }

    @Override
    public void addArticle(Article article) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(article));
    }

    @Override
    public void update(long sequence, Article updatedArticle) {
        String sql = "update articles set title=?, content=? where sequence=?";
        jdbcTemplate.update(sql,
                updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedArticle.getSequence()
        );
    }

    @Override
    public List<Article> findAllArticles() {
        return jdbcTemplate.query("select * from articles", articleRowMapper());
    }

    @Override
    public Optional<Article> findArticleBySequence(long sequence) {
        List<Article> result = jdbcTemplate.query("select * from articles where sequence = ?",
                articleRowMapper(), sequence);
        return result.stream().findAny();
    }

    @Override
    public void clearDatabase() {
        jdbcTemplate.update("delete from articles");
    }

    @Override
    public int getTotalArticleNumber() {
        return jdbcTemplate.queryForObject("select count(*) from articles", Integer.class);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            long sequence = rs.getLong("sequence");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String content = rs.getString("content");
            LocalDateTime publishTime = rs.getTimestamp("publishTime").toLocalDateTime();

            Article article = new Article();
            article.setSequence(sequence);
            article.setWriter(writer);
            article.setTitle(title);
            article.setContent(content);
            article.setPublishTime(publishTime);
            return article;
        };
    }
}
