package codesquad.springcafe.db.article;

import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
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
    public List<ArticleProfileDto> findAllArticles() {
        String sql = """
                select sequence, publishtime, u.nickname as writerNickname, title, content
                from articles a
                left outer join users u
                on a.writer = u.userId
                """;
        return jdbcTemplate.query(
                sql, (rs, rowNum) -> new ArticleProfileDto(
                        rs.getLong("sequence"),
                        rs.getTimestamp("publishtime").toLocalDateTime(),
                        rs.getString("writernickname"),
                        rs.getString("title"),
                        rs.getString("content")
                )
        );
    }

    @Override
    public Optional<ArticleProfileDto> findArticleBySequence(long sequence) {
        String sql = """
                select sequence, publishtime, u.nickname as writerNickname, title, content
                from articles a
                left outer join users u
                on a.writer = u.userId
                where sequence = ?
                """;
        return jdbcTemplate.query(
                sql, new Object[]{sequence}, rs -> {
                    if(rs.next()){
                        return Optional.of(new ArticleProfileDto(
                                sequence,
                                rs.getTimestamp("publishtime").toLocalDateTime(),
                                rs.getString("writernickname"),
                                rs.getString("title"),
                                rs.getString("content")));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public void clearDatabase() {
        jdbcTemplate.update("delete from articles");
    }

    @Override
    public int getTotalArticleNumber() {
        return jdbcTemplate.queryForObject("select count(*) from articles", Integer.class);
    }

}
