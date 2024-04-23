package codesquad.springcafe.repository;

import codesquad.springcafe.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("timestamp", article.getTimestamp());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return jdbcTemplate.query("select * from ARTICLES where id = ?", articleRowMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from ARTICLES", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper(){
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("content")
            );
            article.setId(rs.getLong("id"));
            return article;
        };
    }
}
