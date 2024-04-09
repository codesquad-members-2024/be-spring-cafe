package codesquad.springcafe.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
    private static final String TO_STRING_FORMAT = "[게시글] %s, %s, %s, %s";
    private long id;
    private String writer;
    private String title;
    private String content;
    private String creationTime;
    private long viewCount;

    public Article(String title, String content) {
        this.writer = "익명 사용자"; // TODO: 로그인 구현시 변경
        this.title = title;
        this.content = content;
        this.viewCount = 0;
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

    public String getCreationTime() {
        return creationTime;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public void setCreationTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.creationTime = sdf.format(date);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, title, content, creationTime);
    }
}