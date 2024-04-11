package codesquad.springcafe.article;

public class Article {

    private long articleId;
    private String title;
    private String author;
    private String contents;

    public Article(long articleId, String title, String author, String contents) {
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
}
