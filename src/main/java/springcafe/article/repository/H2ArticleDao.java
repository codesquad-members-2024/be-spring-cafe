package springcafe.article.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springcafe.article.model.Article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class H2ArticleDao implements ArticleDao {

    private JdbcTemplate jdbcTemplate;

    public H2ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article, Long id) {
        jdbcTemplate.update("INSERT into ARTICLES (WRITER, TITLE, CONTENT, USER_ID) values (?,?,?,?)",
                article.getWriter(), article.getTitle(), article.getContent(), id);
    }

    @Override
    public Article findById(Long id) {

        return jdbcTemplate.queryForObject(
                "SELECT ID, WRITER, TITLE, CONTENT, CREATE_DATE, USER_ID FROM ARTICLES WHERE ID =? AND IS_DELETED =FALSE",
                new Object[]{id},
                (rs, rowNum) ->new Article(
                        rs.getString("WRITER"),
                        rs.getString("TITLE"),
                        rs.getString("CONTENT"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getLong("ID"),
                        rs.getLong("USER_ID")
                        )
        );
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query(
                "SELECT ID, WRITER, TITLE, CONTENT, CREATE_DATE, USER_ID FROM ARTICLES WHERE IS_DELETED =FALSE",
                (rs) -> {
                    List<Article> articleList = new ArrayList();
                    while (rs.next()) {
                        String writer = rs.getString("WRITER");
                        String title = rs.getString("TITLE");
                        String content = rs.getString("CONTENT");
                        LocalDateTime createDate = rs.getTimestamp("CREATE_DATE").toLocalDateTime();
                        Long id = rs.getLong("ID");
                        long userId = rs.getLong("USER_ID");

                        Article article = new Article(writer, title, content, createDate, id, userId);
                        articleList.add(article);

                    }
                    return articleList;
                }
        );
    }

    @Override
    public void update(Article article) {
        jdbcTemplate.update(
                "UPDATE ARTICLES SET TITLE =?, CONTENT =? WHERE ID =? AND IS_DELETED =FALSE",
                article.getTitle(), article.getContent(), article.getId());
    }

    @Override
    public void delete(Long articleId) {
        jdbcTemplate.update(
                "UPDATE ARTICLES SET IS_DELETED=TRUE WHERE ID=?",articleId
        );
    }
}

