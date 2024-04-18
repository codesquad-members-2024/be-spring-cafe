package codesquad.springcafe.article;

import codesquad.springcafe.article.database.ArticleDatabase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleDatabase articleDatabase;

    public ArticleService(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    public void save(Article article) {
        articleDatabase.save(article);
    }

    public Article findById(Long articleId) {
        return articleDatabase.findById(articleId);
    }

    public List<Article> findAll() {
        return articleDatabase.findAll();
    }

    public void clear() {
        articleDatabase.clear();
    }

    public void update(ArticleUpdateDto articleUpdateDto, Long articledId) {
        articleDatabase.update(articleUpdateDto.toEntity(), articledId);
    }


}
