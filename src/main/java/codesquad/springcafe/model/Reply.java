package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {

    private Long replyId;
    private Long articleId;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private boolean deleted;

    public Reply(Long articleId, String author, String content) {
        this.articleId = articleId;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.deleted = false;
    }

    public Reply(Long replyId, Long articleId, String author, String content, LocalDateTime createdAt, boolean deleted) {
        this.replyId = replyId;
        this.articleId = articleId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.deleted = deleted;
    }

    public Long getReplyId() {
        return replyId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getDateTime() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }
}
