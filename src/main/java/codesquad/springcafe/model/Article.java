package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Article {
    private static final String TO_STRING_FORMAT = "[게시글] %s, %s, %s, %s";

    private long id;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime creationTime;
    private long viewCount;

    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.viewCount = 0;
    }

    public Article(long id, String userId, String title, String content, LocalDateTime creationTime, long viewCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
        this.viewCount = viewCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, title, content, creationTime);
    }
}