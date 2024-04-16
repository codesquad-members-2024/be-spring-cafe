package springcafe.article.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springcafe.article.model.Article;
import springcafe.user.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class H2ArticleDao implements ArticleRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Article article) {
        jdbcTemplate.update("INSERT into ARTICLES (WRITER, TITLE, CONTENTS) values (?,?,?)",
                article.getWriter(), article.getTitle(), article.getContents());
    }

    @Override
    public Article findById(Long id) {

        return jdbcTemplate.queryForObject(
                "SELECT ID, WRITER, TITLE, CONTENTS, CREATE_DATE FROM ARTICLES WHERE ID =?",
                new Object[]{id},
                (rs, rowNum) ->new Article(
                        rs.getString("WRITER"),
                        rs.getString("TITLE"),
                        rs.getString("CONTENTS"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime(),
                        rs.getLong("ID")
                        )
        );
    }

    @Override
    public Map<Long, Article> findAll() {
        return jdbcTemplate.query(
                "SELECT ID, WRITER, TITLE, CONTENTS, CREATE_DATE FROM ARTICLES",
                (rs) -> {
                    Map<Long, Article> resultMap = new HashMap<>();

                    while (rs.next()) {
                        Long id = rs.getLong("ID");
                        String writer = rs.getString("WRITER");
                        String title = rs.getString("TITLE");
                        String contents = rs.getString("CONTENTS");
                        LocalDateTime createDate = rs.getTimestamp("CREATE_DATE").toLocalDateTime();

                        Article article = new Article(writer, title, contents, createDate, id);
                        resultMap.put(id, article);

                    }
                    return resultMap;
                }
        );
    }
}

