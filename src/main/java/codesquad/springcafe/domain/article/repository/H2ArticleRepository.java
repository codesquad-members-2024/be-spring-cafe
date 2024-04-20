package codesquad.springcafe.domain.article.repository;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.article.repository.util.ArticleRowMapper;
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
        logger.debug("[{}] 아티클 추가 완료", article.getIdentifier());
    }

    @Override
    public List<Article> getArticles() {
        String sql = "select * from ARTICLE";

        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    @Override
    public Article get(String identifier) throws NoSuchArticleException {
        String sql = "select * from ARTICLE where identifier=?";
        List<Article> articles = jdbcTemplate.query(sql, new ArticleRowMapper(), identifier);
        if (articles.isEmpty()) throw new NoSuchArticleException();

        logger.debug("[{}] 아티클 검색 완료", identifier);

        return articles.getFirst();
    }

    @Override
    public void update(Article article) throws NoSuchArticleException {
        String sql = "UPDATE ARTICLE SET title = ?, contents = ?, VIEWCOUNT = ? WHERE identifier = ?";

        int update = jdbcTemplate.update(sql,
                article.getTitle(), article.getContents(), article.getViewCount(), article.getIdentifier());

        if (update == 0) throw new NoSuchArticleException();
        logger.debug("[{}] 아티클 업데이트 완료", article.getIdentifier());
    }

    @Override
    public void delete(String identifier) throws NoSuchArticleException {
        String sql = "delete from ARTICLE where identifier = ?";

        int update = jdbcTemplate.update(sql, identifier);
        if (update == 0) throw new NoSuchArticleException();
        logger.debug("[{}] 아티클 삭제 완료", identifier);
    }
}
