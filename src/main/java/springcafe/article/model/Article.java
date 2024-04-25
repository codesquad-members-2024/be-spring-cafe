package springcafe.article.model;


import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class Article {

    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Long id;
    private Long userId;

    public Article() {
    }

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article(String writer, String title, String content, LocalDateTime createDate, Long id, Long userId) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.id = id;
        this.userId = userId;
    }

    public boolean matchesWriter(String writer){
        return this.writer.equals(writer);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String contents) {
        this.content = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
