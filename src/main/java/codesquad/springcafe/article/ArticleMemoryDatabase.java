package codesquad.springcafe.article;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Collections;

@Repository
public class ArticleMemoryDatabase implements ArticleDatabase{

    private final static Map<Long, Article> articleMap = new LinkedHashMap<>();

    @Override
    public void addArticle(Article article) {
        articleMap.put(article.getArticleId(), article);
    }

    @Override
    public List<Article> getArticleList() {
        return new ArrayList<>(articleMap.values());
    }

    @Override
    public List<Article> getReversedArticleList() {
        List<Article> articleList = getArticleList();
        Collections.reverse(articleList);
        return articleList;
    }

    @Override
    public Article getArticle(long articleId) {
        return articleMap.get(articleId);
    }
}
