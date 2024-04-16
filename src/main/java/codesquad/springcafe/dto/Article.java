package codesquad.springcafe.dto;

import java.beans.ConstructorProperties;
import java.sql.Timestamp;

public class Article {
    private Long id; // 자동 생성 아티클 아이디
    private String writer;
    private String title;
    private String content;
    private Timestamp timestamp;

//    @ConstructorProperties({"writer", "title", "content"})
    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article() {
    }

    public Long getId() {
        return id;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

