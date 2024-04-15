package codesquad.springcafe.dto;

public class ArticleRequestDto {

    private final String writer;
    private final String title;
    private final String contents;

    public ArticleRequestDto(String writer, String title, String contents) {
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
}
