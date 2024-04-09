package codesquad.springcafe.domain;

import java.time.LocalDateTime;

public class Article {
    private int id;
    private LocalDateTime created_datetime;
    private final String author;
    private final String title;
    private final String content;
    private int point;

    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.point = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDateTime(LocalDateTime created){
        this.created_datetime = created;
    }
}
