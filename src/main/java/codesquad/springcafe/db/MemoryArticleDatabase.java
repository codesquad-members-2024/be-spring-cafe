package codesquad.springcafe.db;

import codesquad.springcafe.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MemoryArticleDatabase implements ArticleDatabase {
    private final List<Article> articleDatabase = new ArrayList<>();

    public void addArticle(Article article) {
        articleDatabase.add(article);
    }

    public List<Article> findAllArticles(){
        return articleDatabase;
    }

    public Optional<Article> findArticleBySequence(long sequence){
        return articleDatabase.stream()
                .filter(article -> article.getSequence() == sequence)
                .findFirst();
    }

    public void clearDatabase(){
        articleDatabase.clear();
    }


    public int getTotalArticleNumber(){
        return articleDatabase.size();
    }
}