package codesquad.springcafe.model;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import java.time.LocalDateTime;

public class Article {
    private final Long id;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;
    private boolean deleted;

    public Article(Long id, LocalDateTime timestamp, String writer, String title, String content, boolean deleted) {
        this.id = id;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isValid() {
        return !deleted;
    }

    public boolean isWrittenBy(String userId) {
        return this.writer.equals(userId);
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ArticleInfoDTO toDTO() {
        return new ArticleInfoDTO(id, timestamp, writer, title, content);
    }
}