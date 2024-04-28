package codesquad.springcafe.domain.comment;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long userId;
    private Long articleId;
    private String writer;
    private String content;
    private boolean deleted;
    private final LocalDateTime currentTime;

    public Comment(Long userId, Long articleId, String writer, String content, LocalDateTime currentTime) {
        this.userId = userId;
        this.articleId = articleId;
        this.writer = writer;
        this.content = content;
        this.currentTime = currentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
