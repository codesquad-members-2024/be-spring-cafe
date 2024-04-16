package codesquad.springcafe.database.firstcollection;

import codesquad.springcafe.article.domain.Article;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Articles {

    private final Map<String, Article> articles = new ConcurrentHashMap<>() {
    };

    public void add(Article article) {
        articles.put(article.getIdentifier(), article);
    }

    public List<Article> getArticles() {
        return new ArrayList<>(articles.values());
    }

    public Optional<Article> findArticle(String identifier) {
        return Optional.ofNullable(articles.get(identifier));
    }

}
