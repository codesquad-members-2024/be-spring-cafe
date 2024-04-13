package codesquad.springcafe.web.dto;

import java.time.LocalDateTime;

public class ArticleCreateDto {

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime currentTime;

    public ArticleCreateDto() {
        this.currentTime = LocalDateTime.now();
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

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
