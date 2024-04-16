package codesquad.springcafe.domain;

import java.time.LocalDateTime;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time;
    private String id;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();
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

    public LocalDateTime getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    @Override
    public String toString() {
        return "id: " + id + "writer: " + writer + " title: " + title + " contents: " + contents;
    }
}
