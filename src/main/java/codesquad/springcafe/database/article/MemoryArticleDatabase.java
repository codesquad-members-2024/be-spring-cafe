package codesquad.springcafe.database.article;

import codesquad.springcafe.domain.Article;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
@Qualifier("MemoryArticleDatabase")
public class MemoryArticleDatabase implements ArticleDatabase {
    private final List<Article> articles = new ArrayList<>();

    @Override
    public void saveArticle(Article article) {
        article.setId(articles.size()+1);  // article id 설정
        articles.add(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articles;
    }

    @Override
    public Article getArticleById(int id) {
        return articles.get(id-1);
    }

    @Override
    public int getArticleSize() {
        return articles.size();
    }

    @Override
    public boolean isArticleEmpty(){
        return articles.isEmpty();
    }

    @Override
    public void incrementViewsById(int id) {
        getArticleById(id).increaseViews();
    }

}
