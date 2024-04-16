package codesquad.springcafe.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private static final List<Article> articles = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepository.class);

    public void add(Article article) {
        String sequence = String.valueOf(articles.size() + 1);
        article.setId(sequence);
        articles.add(article);
        logger.info("Added Article:{}", article);
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    public Optional<Article> findById(String articleId) {
        return articles.stream()
                .filter(article -> article.getId().equals(articleId))
                .findAny();
    }

}
