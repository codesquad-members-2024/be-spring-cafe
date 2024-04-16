package springcafe.article.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Article {

    @NotEmpty(message = "글쓴이는 필수 항목입니다.")
    private final String writer;
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    private final String title;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private final String contents;
    private final LocalDateTime createDate;
    private final Long id;

    public Article(String writer, String title, String contents, LocalDateTime createDate, Long id) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.id = id;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
