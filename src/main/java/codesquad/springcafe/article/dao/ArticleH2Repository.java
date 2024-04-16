package codesquad.springcafe.article.dao;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.IArticleRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
public class ArticleH2Repository implements IArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleH2Repository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // PK는 INT타입으로 AUTO_INCREMENT 를 사용하여 DB가 관리하도록 설정.
    public void save(Article article) {
        final String sql = "INSERT INTO ARTICLE (writer, title, contents, createAt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents(), article.getCreateAt());
    }

    public Collection<Article> findAll() {
        return null;
    }

    public int size() {
        return 0;
    }

    public Optional<Article> findBy(int id) {
//        return Optional.ofNullable(articles.get(id));
        return Optional.empty();
    }

}
