package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class ArticleUpdateDTO {

    private final String title;
    private final String content;

    public ArticleUpdateDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toArticle(Long index, LocalDateTime timestamp, String writer) {
        return new Article(index, timestamp, writer, title, content);
    }
}
