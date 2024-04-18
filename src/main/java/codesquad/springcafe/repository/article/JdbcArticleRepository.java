package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createArticle(ArticleDto articleDto) {
        return null;
    }

    @Override
    public List<Article> findAllArticles() {
        return null;
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return Optional.empty();
    }

    @Override
    public void clear() {

    }
}
