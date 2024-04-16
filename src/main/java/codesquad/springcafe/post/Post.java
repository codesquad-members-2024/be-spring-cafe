package codesquad.springcafe.post;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime dateTime;

    public Post() {

    }

    public Post(Long id, String title, String author, String content, LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", title=" + title + ", author=" + author + ", content=" + content + ", dateTime=" + dateTime + "]";
    }
}
