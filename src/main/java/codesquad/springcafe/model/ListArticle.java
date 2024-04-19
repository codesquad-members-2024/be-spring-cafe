package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListArticle {
    private final long id;
    private final String userId;
    private final String title;
    private final LocalDateTime creationTime;
    private final long viewCount;

    public ListArticle(long id, String userId, String title, LocalDateTime creationTime, long viewCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.creationTime = creationTime;
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViewCount() {
        return viewCount;
    }
}