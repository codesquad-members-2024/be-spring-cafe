package codesquad.springcafe.article;

import java.time.LocalDateTime;

public class ArticleCraetionDto {
    private String writer;
    private String title;
    private String contents;

    public ArticleCraetionDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article(writer, title, contents, LocalDateTime.now());
    }
}
