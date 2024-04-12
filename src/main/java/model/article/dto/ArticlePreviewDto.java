package model.article.dto;

public class ArticlePreviewDto {
    private final Long articleId;
    private final String userId;
    private final String title;
    private final String creationDate;

    public ArticlePreviewDto(Long articleId, String userId, String title, String creationDate) {
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.creationDate = creationDate;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
