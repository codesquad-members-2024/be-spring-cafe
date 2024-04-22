package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Map<Long, Article> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Long save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article);
        return article.getId();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAllArticle() {
        return new ArrayList<>(store.values());
    }


}
