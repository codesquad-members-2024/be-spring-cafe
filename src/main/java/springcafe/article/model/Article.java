package springcafe.article.model;


import java.time.LocalDateTime;

public class Article {

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private Long id;
    private Long usersId;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(String writer, String title, String contents, LocalDateTime createDate, Long id, Long usersId) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.id = id;
        this.usersId = usersId;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String contents) {
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public Long getUsersId() {
        return usersId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
