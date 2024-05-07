package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.repository.ArticleRepository;
import codesquad.springcafe.dto.EditArticleForm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class H2ArticleRepository implements ArticleRepository {
    private final static String WRITER_KEY = "writer";
    private final static String TITLE_KEY = "title";
    private final static String CONTENTS_KEY = "contents";
    private final static String TIME_KEY = "time";
    private final static String ID_KEY = "id";
    private final static String EDITED_KEY = "edited";
    private final static String DELETE_KEY = "deleted";

    private final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Article> articleRowMapper = (resultSet, rowNum) -> {
        Article article = new Article(
                resultSet.getString(WRITER_KEY),
                resultSet.getString(TITLE_KEY),
                resultSet.getString(CONTENTS_KEY),
                resultSet.getTimestamp(TIME_KEY).toLocalDateTime(),
                resultSet.getBoolean(EDITED_KEY)
        );
        article.setId(resultSet.getLong(ID_KEY));
        return article;
    };

    public H2ArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article add(Article article) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Articles")
                .usingGeneratedKeyColumns(ID_KEY);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(WRITER_KEY, article.getWriter());
        parameters.put(TITLE_KEY, article.getTitle());
        parameters.put(CONTENTS_KEY, article.getContents());
        parameters.put(TIME_KEY, article.getTime());
        parameters.put(EDITED_KEY, false);
        parameters.put(DELETE_KEY, false);

        Long key = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        article.setId(key);
        logger.debug("게시글 {} 저장 완료", article.getContents());
        return article;
    }

    @Override
    public List<Article> getAll() {
        return jdbcTemplate.query("SELECT * FROM Articles WHERE deleted=false", articleRowMapper);
    }

    @Override
    public Optional<Article> getById(Long articleId) {
        final String SELECT_ARTICLE = "SELECT * FROM Articles WHERE id= ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ARTICLE, articleRowMapper, articleId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void edit(String articleId, EditArticleForm editArticleForm) {
        final String UPDATE_ARTICLE = "UPDATE Articles SET title=?, contents=?, edited=? WHERE id=?";
        jdbcTemplate.update(UPDATE_ARTICLE, editArticleForm.getTitle(), editArticleForm.getContents(), true, articleId);
        logger.debug("{} 번글 수정 완료", articleId);
    }

    public void delete(String articleId) {
        final String DELETE_ARTICLE = "UPDATE Articles SET DELETED = true WHERE id=?";
        jdbcTemplate.update(DELETE_ARTICLE, articleId);
    }
}
