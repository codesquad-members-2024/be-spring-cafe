package codesquad.springcafe.domain;

import codesquad.springcafe.dto.ArticleDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime created;
    private long views;

    public Article(long id, String writer, String title, String content, LocalDateTime created, long views) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.created = created;
        this.views = views;
    }

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.created = LocalDateTime.now();
        this.views = 0L;
    }

    public ArticleDto toDto() {
        return new ArticleDto(title, content);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void increaseViews() {
        views++;
    }

    public long getId() {
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

    public String getCreated() {
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViews() {
        return views;
    }
}
