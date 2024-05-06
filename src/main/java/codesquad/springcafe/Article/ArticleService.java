package codesquad.springcafe.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article addArticle(Article article) {
        return articleRepository.addArticle(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findByIndex(int number) {
        return articleRepository.findByIndex(number);
    }

    public int articleSize() {
        return articleRepository.articleSize();
    }

    public void clear() {
        articleRepository.clear();
    }
}
