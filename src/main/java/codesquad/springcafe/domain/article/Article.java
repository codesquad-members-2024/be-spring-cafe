package codesquad.springcafe.domain.article;

import java.time.LocalDateTime;

public class Article {

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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
