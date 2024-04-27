package codesquad.springcafe.domain.article;

import codesquad.springcafe.web.dto.ArticleUpdateDto;
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
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", article.getUserId());
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("currentTime", article.getCurrentTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
    }

    @Override
    public void update(Long id, ArticleUpdateDto articleUpdateDto) {
        jdbcTemplate.update("update article set title = ?, contents = ? where id = ?",
                articleUpdateDto.getTitle(), articleUpdateDto.getContents(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from article where id = ?", id);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(
                    rs.getLong("user_id"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("currentTime").toLocalDateTime()
            );
            article.setId(rs.getLong("id"));
            return article;
        };
    }
}
