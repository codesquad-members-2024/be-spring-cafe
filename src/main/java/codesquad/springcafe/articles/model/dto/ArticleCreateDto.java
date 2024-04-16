package codesquad.springcafe.articles.model.dto;

public class ArticleCreateDto {
    private final String userId;
    private final String title;
    private final String content;

    public ArticleCreateDto(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
