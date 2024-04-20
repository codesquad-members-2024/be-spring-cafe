package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.article.repository.util.ArticleRowMapper;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class H2ArticleRepository implements ArticleRepository{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Article article) {
        String sql = "insert into ARTICLE (identifier, writer, title, contents, createTime) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                article.getIdentifier(), article.getWriter(), article.getTitle(), article.getContents(), article.getRoughCrateTime());
        logger.info("[{}] 아티클 추가 완료", article.getIdentifier());
    }

    @Override
    public List<Article> getArticles() {
        String sql = "select * from ARTICLE";

        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    @Override
    public Article getArticle(String identifier) throws NoSuchArticleException {
        String sql = "select * from ARTICLE where identifier=?";
        List<Article> articles = jdbcTemplate.query(sql, new ArticleRowMapper(), identifier);
        if (articles.isEmpty()) throw new NoSuchArticleException();

        logger.info("[{}] 아티클 검색 완료", identifier);

        return articles.getFirst();
    }
}
