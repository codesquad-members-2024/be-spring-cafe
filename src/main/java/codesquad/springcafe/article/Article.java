package codesquad.springcafe.article;

public class Article {

    private Long articleId;
    private String title;
    private String author;
    private String contents;
    private String userId;
    private boolean deleted;

    public Article(Long articleId, String title, String author, String contents, String userId,
        boolean deleted) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
        this.deleted = deleted;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void update(Article article) {
        this.title = article.title;
        this.contents = article.contents;
    }
}
