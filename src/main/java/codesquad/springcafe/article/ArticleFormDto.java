package codesquad.springcafe.article;

public class ArticleFormDto {
    private String writer;
    private String title;
    private String contents;

    public ArticleFormDto(String writer, String title, String contents) {
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
