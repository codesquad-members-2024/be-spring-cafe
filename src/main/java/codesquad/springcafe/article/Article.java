package codesquad.springcafe.article;

public class Article {

    private Long articleId;
    private String title;
    private String author;
    private String contents;
    private String userId;

    public Article(Long articleId, String title, String author, String contents, String userId) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
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

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void update(Article article) {
        this.title = article.title;
        this.contents = article.contents;
    }
}
