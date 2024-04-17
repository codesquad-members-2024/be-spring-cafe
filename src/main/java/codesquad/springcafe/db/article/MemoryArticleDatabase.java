package codesquad.springcafe.db.article;

import codesquad.springcafe.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class MemoryArticleDatabase implements ArticleDatabase {
    private final List<Article> articleDatabase = new ArrayList<>();

    public void addArticle(Article article) {
        articleDatabase.add(article);
    }

    @Override
    public void update(long sequence, Article updatedArticle) {
        IntStream.range(0, articleDatabase.size())
                .filter(i -> articleDatabase.get(i).getSequence() == sequence)
                .findFirst()
                .ifPresent(i -> articleDatabase.set(i, updatedArticle));
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