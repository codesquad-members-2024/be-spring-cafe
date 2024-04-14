package codesquad.springcafe.domain;

import java.time.LocalDateTime;

public class Article {
    private String writer;
    private String title;
    private String content;
    private LocalDateTime time;


    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.time = LocalDateTime.now();
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

    public LocalDateTime getTime() {
        return time;
    }


    @Override
    public String toString() {
        return "writer: " + writer + " title: " + title + " content: " + content;
    }
    
}
