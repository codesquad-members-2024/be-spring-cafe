package codesquad.springcafe.articles.model.dto;

public class ArticleUpdateDto {
    private final String title;
    private final String content;

    public ArticleUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
