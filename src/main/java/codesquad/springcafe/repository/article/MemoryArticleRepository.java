package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.ArticleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();

    @Override
    public void createArticle(Article article) {
        long id = articles.size() + 1;
        article.setId(id);
        articles.put(id, article);
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public void updateViews(long id) {
        Article article = findById(id).orElseThrow(() -> new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다."));
        article.setViews(article.getViews() + 1);
    }

    @Override
    public void updateArticle(long id, ArticleDto articleDto) {
        Article article = findById(id).orElseThrow(() -> new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다."));
        article.update(articleDto);
    }

    @Override
    public void deleteArticle(long id) {
        Article article = findById(id).orElseThrow(() -> new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다."));
        articles.remove(id);
    }
}