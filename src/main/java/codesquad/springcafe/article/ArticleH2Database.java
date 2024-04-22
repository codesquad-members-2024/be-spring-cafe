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

    private static Long sequence = 0L;

    @Autowired
    public ArticleH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.sequence = getMaxArticleId();
    }

    // 저장되어 있는 Article 중 가장 큰 articleId를 찾아 sequence로 사용하기 위함입니다.
    private Long getMaxArticleId() {
        Long numOfArticle = jdbcTemplate.queryForObject("SELECT MAX(articleId) FROM MAIN.ARTICLES", Long.class);
        return numOfArticle == null ? 0L : numOfArticle;
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
        article.setArticleId(++sequence);
        String sql = "INSERT INTO MAIN.ARTICLES (articleid, writer, title, content, createdTime) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getArticleId(), article.getWriter(), article.getTitle(), article.getContent(), article.getCreatedTime());
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
}
