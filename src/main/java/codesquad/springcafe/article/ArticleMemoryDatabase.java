package codesquad.springcafe.article;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleMemoryDatabase implements ArticleDatabase{

    private final static Map<Long, Article> articleMap = new LinkedHashMap<>();
    private static long sequence = 0L;

    @Override
    public void addArticle(Article article) {
        article.setArticleId(++sequence);
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

    @Override
    public void updateArticle(Article article) {

    }
}
