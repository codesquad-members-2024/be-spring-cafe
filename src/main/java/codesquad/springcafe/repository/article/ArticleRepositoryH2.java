package codesquad.springcafe.repository.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
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
        String sql = "select ARTICLE_ID, TITLE, CONTENTS, CREATED_BY, CREATED_AT, DELETED from ARTICLE where ARTICLE_ID = ? and DELETED is false";
        try {
            return Optional.ofNullable(template.queryForObject(sql, articleRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select A.ARTICLE_ID, A.TITLE, A.CREATED_BY, A.CREATED_AT, COALESCE(COUNT(C.DELETED), 0) AS comment_size "
                + "from ARTICLE A LEFT JOIN COMMENT C ON A.ARTICLE_ID = C.ARTICLE_ID AND C.DELETED = false "
                + "where A.DELETED is false and A.CREATED_AT >= dateadd(day, -3, current_date) "
                + "group by A.ARTICLE_ID, A.TITLE, A.CREATED_BY, A.CREATED_AT "
                + "order by A.CREATED_AT desc ";
        return template.query(sql, articleRowMapperForList());
    }

    @Override
    public Page<Article> findAll(PageRequest pageRequest) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template);

        String findSql = "select A.ARTICLE_ID, A.TITLE, A.CREATED_BY, A.CREATED_AT, COALESCE(COUNT(C.DELETED), 0) AS comment_size "
                + "from ARTICLE A LEFT JOIN COMMENT C ON A.ARTICLE_ID = C.ARTICLE_ID AND C.DELETED = false "
                + "where A.DELETED is false "
                + "group by A.ARTICLE_ID, A.TITLE, A.CREATED_BY, A.CREATED_AT "
                + "order by A.CREATED_AT " + String.format("%s ", pageRequest.getSort().val);
        findSql += "limit :offset, :pageSize;";

        String totalSql = "select COUNT(*) as total FROM ARTICLE WHERE deleted is false;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("offset", pageRequest.getOffset());
        parameters.addValue("pageSize", pageRequest.getPageSize());

        List<Article> articles = namedTemplate.query(findSql, parameters, articleRowMapperForList());
        Integer total = template.queryForObject(totalSql, Integer.class);

        return new Page<>(articles, PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageRequest.getSort()), total);
    }

    @Override
    public void update(UpdateArticle updateParam) {
        String sql = "update ARTICLE set TITLE = ?, CONTENTS = ? where ARTICLE_ID = ?";
        template.update(sql, updateParam.getTitle(), updateParam.getContents(), updateParam.getId());
    }

    @Override
    public void delete(long id) throws DataIntegrityViolationException {
        String sql = "delete from ARTICLE where ARTICLE_ID = ?";
        template.update(sql, id);
    }

    @Override
    public void softDelete(long id) {
        String sql = "update ARTICLE set DELETED = ? where ARTICLE_ID = ?";
        template.update(sql, true, id);
    }

    @Override
    public void restore(long id) {
        String sql = "update ARTICLE set DELETED = ? where ARTICLE_ID = ?";
        template.update(sql, false, id);
    }

    @Override
    public void clear() {
        String sql = "alter table if exists article drop constraint if exists fk_created_by;"
                + "alter table if exists comment drop constraint if exists fk_comment_created_by;"
                + "alter table if exists comment drop constraint if exists fk_comment_article_id;"
                + "TRUNCATE TABLE ARTICLE; ALTER TABLE ARTICLE ALTER COLUMN ARTICLE_ID RESTART WITH 1";
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
            article.setDeleted(rs.getBoolean("deleted"));
            return article;
        };
    }

    private RowMapper<Article> articleRowMapperForList() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("article_id"));
            article.setTitle(rs.getString("title"));
            article.setCreatedBy(rs.getString("created_by"));
            article.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            article.setCommentSize(rs.getInt("comment_size"));

            return article;
        };
    }
}
