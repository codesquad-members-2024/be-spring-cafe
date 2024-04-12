package model.article.dto;

public class ArticleContentDto {
    private final String userId;
    private final String title;
    private final String content;
    private final String creationDate;

    public ArticleContentDto(String userId, String title, String content, String creationDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
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

    public String getCreationDate() {
        return creationDate;
    }

}
