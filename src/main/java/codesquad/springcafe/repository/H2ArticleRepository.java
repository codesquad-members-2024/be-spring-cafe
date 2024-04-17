package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {

    private final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleRowMapper = (resultSet, rowNum) -> {
        Article article = new Article(
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("time").toLocalDateTime()
        );
        article.setId(resultSet.getLong("id"));
        return article;
    };

    public H2ArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Article article) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Articles")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("time", article.getTime());

        Long key = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        article.setId(key);
        logger.debug("게시글 {} 저장 완료", article.getContents());
    }

    @Override
    public List<Article> getAll() {
        return jdbcTemplate.query("SELECT * FROM Articles", articleRowMapper);
    }

    @Override
    public Article getById(Long articleId) {
        final String SELECT_ARTICLE = "SELECT * FROM Articles WHERE id= ?";
        return jdbcTemplate.queryForObject(SELECT_ARTICLE, articleRowMapper, articleId);
    }
}
