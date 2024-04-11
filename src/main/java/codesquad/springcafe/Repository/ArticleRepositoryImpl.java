package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
    List<Article> ArticleDB = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setId((long) (ArticleDB.size()+1));
        ArticleDB.add(article);
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        return ArticleDB.stream().filter(article -> article.getId().equals(articleId)).findAny();
    }

    @Override
    public List<Article> findAll() {
        return ArticleDB;
    }
}