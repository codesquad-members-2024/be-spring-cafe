package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.List;
import java.util.Optional;

public class JdbcArticleRepository implements ArticleRepository {
    @Override
    public Article createArticle(ArticleDto articleDto) {
        return null;
    }

    @Override
    public List<Article> findAllArticles() {
        return null;
    }

    @Override
    public Optional<Article> findByArticleId(Long articleId) {
        return Optional.empty();
    }
}
