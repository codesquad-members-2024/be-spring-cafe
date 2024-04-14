package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long articleId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private long views;

    public Article(Long articleId, String writer, String title, String content, LocalDateTime localDateTime,
                   long views) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public String getLocalDateTime() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", localDateTime=" + localDateTime +
                ", views=" + views +
                '}';
    }
}
