package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time;
    private Long id;
    private boolean edited;
    private boolean deleted;

    public Article(String writer, String title, String contents, LocalDateTime time, boolean edited) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
        this.edited = edited;
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

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEdited(boolean isEdited) {
        edited = isEdited;
    }

    public boolean getEdited() {
        return edited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "id: " + id + "writer: " + writer + " title: " + title + " contents: " + contents;
    }
}
