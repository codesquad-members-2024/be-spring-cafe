package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UpdatedArticle {
    private String title;
    private String content;
    private LocalDateTime modifiedTime;

    public UpdatedArticle(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
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