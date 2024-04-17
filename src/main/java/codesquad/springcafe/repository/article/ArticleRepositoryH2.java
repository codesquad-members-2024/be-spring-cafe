package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.article.Article;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ArticleRepositoryH2 implements ArticleRepository {

    private final JdbcTemplate template;

    @Autowired
    public ArticleRepositoryH2(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        String sql = "INSERT INTO ARTICLE(TITLE, CONTENTS, CREATED_BY, CREATED_AT) VALUES(?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContents());
            ps.setString(3, article.getCreatedBy());
            ps.setTimestamp(4, Timestamp.valueOf(article.getCreatedAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("pk를 생성하지 못했습니다");
        }

        article.setId(key.longValue());
        return article;
    }

    @Override
    public Optional<Article> findById(long id) {
        String sql = "select ARTICLE_ID, TITLE, CONTENTS, CREATED_BY, CREATED_AT from ARTICLE where ARTICLE_ID = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, articleRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select ARTICLE_ID, TITLE, CONTENTS, CREATED_BY, CREATED_AT from ARTICLE where CREATED_AT >= dateadd(day, -3, current_date) order by CREATED_AT desc ";
        return template.query(sql, articleRowMapper());
    }

    @Override
    public void clear() {
        String sql = "ALTER TABLE ARTICLE ALTER COLUMN ARTICLE_ID RESTART WITH 1";
        template.update(sql);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("article_id"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedBy(rs.getString("created_by"));
            article.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return article;
        };
    }
}
