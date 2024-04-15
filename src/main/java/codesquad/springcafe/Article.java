package codesquad.springcafe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private final String writer;
    private String title;
    private String content;
    private final Long articleId;
    private final LocalDateTime createdTime;
    private static Long idCounter = 0L;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.articleId = ++idCounter;
        this.createdTime = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

//    글쓴이를 수정하지 않기 때문에 제거
//    public void setWriter(String writer) {
//        this.writer = writer;
//    }

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

    public Long getArticleId() {
        return articleId;
    }

    public String getFormattedTime() {
        String formattedTime = this.createdTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        return formattedTime;
    }

    @Override
    public String toString() {
        return "articleId : " + articleId + ", writer : " + writer + ", title : " + title + ", content : " + content;
    }
}
