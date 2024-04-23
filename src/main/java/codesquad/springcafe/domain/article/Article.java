package codesquad.springcafe.domain.article;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private final LocalDateTime currentTime;

    public Article(String writer, String title, String contents, LocalDateTime currentTime) {
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

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
