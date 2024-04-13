package codesquad.springcafe.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final List<Article> articles = new ArrayList<>();

    public void save(Article article) {
        articles.add(article);
        log.debug("게시글 갯수 : {}", articles.size());
    }
}
