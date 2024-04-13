package codesquad.springcafe.article;

import java.time.LocalDateTime;

public class ArticleDto {
    private Long point;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    public ArticleDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
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

    public void setPoint(Long point) {
        this.point = point;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
