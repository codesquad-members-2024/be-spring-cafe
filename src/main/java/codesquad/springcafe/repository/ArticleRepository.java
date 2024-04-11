package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

    private static final List<Article> articles = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(ArticleRepository.class);

    public void add(Article article) {
        articles.add(article);
        logger.info("SAVED ARTICLE : {}", article.toString());
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }
}