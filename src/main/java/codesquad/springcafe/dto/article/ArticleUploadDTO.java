package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class ArticleUploadDTO {
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;
    private boolean deleted;

    public ArticleUploadDTO(String writer, String title, String content) {
        this.timestamp = LocalDateTime.now();
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.deleted = false;
    }

    public Article toArticle(Long id) {
        return new Article(id, timestamp, writer, title, content, deleted);
    }
}