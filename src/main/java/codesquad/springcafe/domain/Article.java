package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long articleId;
    private String writer;
    private String title;
    private String description;
    private LocalDateTime localDateTime;
    private long views;

    public Article(Long articleId, String writer, String title, String description, LocalDateTime localDateTime,
                   long views) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.description = description;
        this.localDateTime = localDateTime;
        this.views = views;
    }

    public void increaseViews() {
        views++;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalDateTime() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViews() {
        return views;
    }
}
