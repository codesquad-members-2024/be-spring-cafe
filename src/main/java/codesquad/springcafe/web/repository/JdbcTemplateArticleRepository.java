package codesquad.springcafe.web.repository;

import codesquad.springcafe.web.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Article save(Article article) {
        // 임시로 userId를 "guest"로 설정
        String userId = "ha";
        // article 객체의 writer 필드에 userId 설정
        article.setWriter(userId);

        // contents의 줄바꿈을 <br> 태그로 변경
        String contents = article.getContents().replaceAll("\n", "<br>");
        article.setContents(contents);

        // SQL 쿼리 실행
        String sql = "INSERT INTO articles(writer, title, contents) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), contents);

        return article;
    }

    @Override
    public List<Article> articlesAll() {
        String sql = "SELECT * FROM articles";
        List<Article> articles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Article.class));
        return articles;
    }


    @Override
    public Optional<Article> findByIndex(Long articleId) {
        String sql = "SELECT * FROM articles WHERE articleId = ?";
        List<Article> articles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Article.class), articleId);
        return articles.isEmpty() ? Optional.empty() : Optional.of(articles.get(0));
    }
}
