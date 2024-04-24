package codesquad.springcafe.dto;

public class ArticleRequestDto {

    private final String title;
    private final String contents;

    public ArticleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
