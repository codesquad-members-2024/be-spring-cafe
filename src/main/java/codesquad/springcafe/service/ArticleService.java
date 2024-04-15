package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final List<Article> articles = new ArrayList<>();

    public Article createArticle(String writer, String title, String contents) {
        Long index = (long) articles.size() + 1;
        Article newArticle = new Article(index, LocalDateTime.now(), writer, title, contents);
        articles.add(newArticle);
        return newArticle;
    }

    public List<Article> findAllArticles() {
        return new ArrayList<>(articles);
    }

    public Article findArticleByIndex(Long index) {
        Optional<Article> articleOptional = articles.stream()
            .filter(article -> article.getIndex().equals(index))
            .findAny();
        return articleOptional.orElse(null);
    }
}
