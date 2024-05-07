package codesquad.springcafe.web.dto;

public class ArticleUpdateDto {

    private String title;
    private String contents;

    public ArticleUpdateDto(String title, String contents) {
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
