package codesquad.springcafe.article;

public class Article {

    private long articleId = -1;
    private String title;
    private String author;
    private String contents;

    public Article(String title, String author, String contents) {
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

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
