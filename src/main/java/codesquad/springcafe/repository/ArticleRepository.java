package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public void save(ArticleRequestDto articleRequestDto) {
        long articleId = sequence.incrementAndGet();
        Article article = new Article(articleId, articleRequestDto);
        articles.put(articleId, article);
        System.out.println(article);
    }

    public Article findById(Long articleId) {
        return articles.get(articleId);
    }

}
