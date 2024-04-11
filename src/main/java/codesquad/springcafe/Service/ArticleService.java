package codesquad.springcafe.Service;

import codesquad.springcafe.Domain.Article;
import codesquad.springcafe.Repository.ArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository; //구현체 주입
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public Article findArticleById(long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Article> findAllArticle() {
        return articleRepository.findAll();
    }
}
