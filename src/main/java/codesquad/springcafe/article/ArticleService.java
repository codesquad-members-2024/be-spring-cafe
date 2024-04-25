package codesquad.springcafe.article;

import codesquad.springcafe.article.database.ArticleDatabase;
import codesquad.springcafe.article.dto.ArticleUpdateDto;
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

    public void delete(Long articleId) {
        articleDatabase.delete(articleId);
    }

    public boolean isAuthenticated(String nickname, Long articleId) {
        return findById(articleId).getAuthor().equals(nickname);
    }


}
