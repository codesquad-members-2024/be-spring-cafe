package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final List<Article> articles = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    @Override
    public Article save(Article article) {
        long sequence = (articles.size() + 1);
        article.setId(sequence);
        articles.add(article);
        logger.info("SAVED ARTICLE : {}", article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        return articles.stream()
                .filter(article -> article.getId().equals(articleId))
                .findAny();
    }
}