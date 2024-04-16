package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Article {
    private static final String EMPTY = "";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String TO_STRING_FORMAT = "[게시글] %s, %s, %s, %s";

    private long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime creationTime;
    private long viewCount;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.creationTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.viewCount = 0;
    }

    public Article(long id, String writer, String title, String content, LocalDateTime creationTime, long viewCount) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
        this.viewCount = viewCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedCreationTime() {
        if (creationTime == null) {
            return EMPTY;
        }
        return creationTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime localDateTime) {
        this.creationTime = localDateTime;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, title, content, creationTime);
    }
}