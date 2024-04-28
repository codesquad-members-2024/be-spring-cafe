package codesquad.springcafe.domain.article;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long userId;
    private String writer;
    private String title;
    private String contents;
    private boolean deleted;
    private final LocalDateTime currentTime;

    public Article(Long userId, String writer, String title, String contents, LocalDateTime currentTime) {
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
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
