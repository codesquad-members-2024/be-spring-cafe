package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

    private static final List<Article> articles = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(ArticleRepository.class);

    public void add(Article article) {
        long sequence = (articles.size() + 1);
        article.setId(sequence);
        articles.add(article);
        logger.info("SAVED ARTICLE : {}", article);
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    public Optional<Article> findById(Long articleId) {
        return articles.stream()
                .filter(article -> article.getId().equals(articleId))
                .findAny();
    }
}