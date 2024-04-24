package codesquad.springcafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDateTime;

    public Article(String writer,
                   String title,
                   String content,
                   LocalDateTime createDateTime) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
