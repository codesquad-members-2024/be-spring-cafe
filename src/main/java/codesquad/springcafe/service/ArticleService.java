package codesquad.springcafe.service;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    private final ArticleDatabase articleDatabase;

    public ArticleService(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    public boolean isPresent(Long id) {
        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        return optionalArticle.isPresent();
    }

    public Set<Long> getOwnArticleIds(String userNickname) {
        List<Article> articles = articleDatabase.findAll(userNickname);
        return null;
    }

    public Article getArticle(Long id) {
        return articleDatabase.findBy(id).orElse(null);
    }

}
