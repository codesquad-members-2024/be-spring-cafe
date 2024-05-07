package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Comment {
    private static final String TO_STRING_FORMAT = "[댓글] %s, %s, %s, %s";

    private long id;
    private long articleId;
    private String userId;
    private String content;
    private boolean isModified;
    private boolean isDeleted;
    private LocalDateTime creationTime;
    private LocalDateTime modifiedTime;

    public Comment(String userId, long articleId, String content) {
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.creationTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public Comment(long id, long articleId, String userId, String content, boolean isModified, boolean isDeleted,
                   LocalDateTime creationTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.isModified = isModified;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getFormattedModifiedTime() {
        return modifiedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, userId, content, creationTime);
    }
}
