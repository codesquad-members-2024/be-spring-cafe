package codesquad.springcafe.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class ArticleH2Database implements ArticleDatabase {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Article> articleRowMapper = (rs, rowNum) -> {
        Article article = new Article(rs.getString("writer"),
                                    rs.getString("title"),
                                    rs.getString("content"));
        article.setArticleId(rs.getLong("articleId"));
        article.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
        return article;
    };

    @Override
    public void addArticle(Article article) {
        String sql = "INSERT INTO MAIN.ARTICLES (writer, title, content, createdTime) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContent(), article.getCreatedTime());
    }

    @Override
    public List<Article> getArticleList() {
        String sql = "SELECT * FROM MAIN.ARTICLES";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper);
        return articleList;
    }

    // 인덱스가 있는 PrimaryKey인 ArticleID를 통해 정렬한 뒤 가져오도록 했습니다.
    @Override
    public List<Article> getReversedArticleList() {
        String sql = "SELECT * FROM MAIN.ARTICLES ORDER BY ARTICLEID DESC LIMIT 10";
        return jdbcTemplate.query(sql, articleRowMapper);
    }

    @Override
    public Article getArticle(long articleId) {
        String sql = "SELECT * FROM MAIN.ARTICLES WHERE articleId = ?";
        List<Article> articleList = jdbcTemplate.query(sql, articleRowMapper, articleId);
        return articleList.isEmpty() ? null : articleList.get(0);
    }

    @Override
    public void updateArticle(Article article) {
        String sql = "UPDATE MAIN.ARTICLES SET title = ?, content = ? WHERE articleId = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), article.getArticleId());
    }

    @Override
    public void deleteArticle(long articleId) {
        String sql = "DELETE FROM MAIN.ARTICLES WHERE articleId = ?";
        jdbcTemplate.update(sql, articleId);
    }
}
