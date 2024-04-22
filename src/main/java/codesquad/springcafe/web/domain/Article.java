package codesquad.springcafe.web.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public Article() {
        // 기본 생성자
    }
    public Article(Long articleId, String writer, String title, String contents, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
    }


    public Long getArticle_id() {
        return articleId;
    }

    public void setArticle_id(Long article_id) {
        this.articleId = article_id;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

//    public String getCreatedAtFormatted() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return createdAt.format(formatter);
//    }

    public LocalDateTime getCreatedAtFormatted() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
