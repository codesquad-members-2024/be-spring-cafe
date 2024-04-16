package codesquad.springcafe.database;

import codesquad.springcafe.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        return new ArrayList<>(articleMap.values()).reversed();
    }

    @Override
    public Article getArticle(long articleId) {
        return articleMap.get(articleId);
    }
}
