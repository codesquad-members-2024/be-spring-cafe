package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{

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
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }
}