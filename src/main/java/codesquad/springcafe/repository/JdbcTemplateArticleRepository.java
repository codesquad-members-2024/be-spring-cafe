package codesquad.springcafe.repository;

import codesquad.springcafe.dto.Article;

import java.util.List;
import java.util.Optional;

public class JdbcTemplateArticleRepository implements ArticleRepository{
    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        return null;
    }
}
