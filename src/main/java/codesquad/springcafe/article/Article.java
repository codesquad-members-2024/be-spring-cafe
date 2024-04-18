package codesquad.springcafe.article;

public class Article {

    private Long articleId;
    private String title;
    private String author;
    private String contents;

    public Article(Long articleId, String title, String author, String contents) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.contents = contents;
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

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void update(Article article) {
        this.title = article.title;
        this.contents = article.contents;
    }
}
