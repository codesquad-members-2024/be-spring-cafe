package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into article (writer, title, contents) " +
                "values (:writer, :title, :contents)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        article.setId(key);
        return article;
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM Article";

        return template.query(sql, (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            return article;
        });
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select id, writer, title, contents from article where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Article article = template.queryForObject(sql, param, articleRowMapper());
            return Optional.of(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Article> articleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Article.class);
    }
}