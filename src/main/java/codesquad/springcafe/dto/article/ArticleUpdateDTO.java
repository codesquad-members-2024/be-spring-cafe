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

    public Article toArticle(Article originalArticle) {
        Long id = originalArticle.getId();
        LocalDateTime timestamp = originalArticle.getTimeStamp();
        String writer = originalArticle.getWriter();
        return new Article(id, timestamp, writer, title, content, false);
    }
}
