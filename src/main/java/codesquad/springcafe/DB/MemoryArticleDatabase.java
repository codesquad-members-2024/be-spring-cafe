package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
@Qualifier("MemoryArticleDatabase")
public class MemoryArticleDatabase implements ArticleDatabase{
    private final List<Article> articles = new ArrayList<>();

    public void saveArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public Article getArticleById(int id) {
        return articles.get(id-1);
    }

    public int getArticleSize() {
        return articles.size();
    }

    public boolean isArticleEmpty(){
        return articles.isEmpty();
    }

}
