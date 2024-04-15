package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
}
