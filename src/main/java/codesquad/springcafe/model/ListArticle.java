package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListArticle {
    private final long id;
    private final String userId;
    private final String title;
    private boolean isModified;
    private LocalDateTime creationTime;
    private LocalDateTime modifiedTime;
    private final long viewCount;

    public ListArticle(long id, String userId, String title, boolean isModified, LocalDateTime creationTime,
                       LocalDateTime modifiedTime, long viewCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.isModified = isModified;
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.viewCount = viewCount;
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

    public boolean isModified() {
        return isModified;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getFormattedModifiedTime() {
        return modifiedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViewCount() {
        return viewCount;
    }
}