package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UpdatedArticle {
    private long id;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime modifiedTime;

    public UpdatedArticle(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.modifiedTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}