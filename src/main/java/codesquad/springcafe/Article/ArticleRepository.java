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

    public Article add(Article article) {
        articles.add(article);
        logger.info("Added Article:{}", article);
        return article;
    }

    public List<Article> findAll() {
        return articles;
    }

    public Optional<Article> findByIndex(int number) {
        int index = number - 1;
        if(index >=0 && index < articles.size()){
            return Optional.of(articles.get(index));
        } else{
            return Optional.empty();
        }
    }

    public int articleSize(){return articles.size();}

}
