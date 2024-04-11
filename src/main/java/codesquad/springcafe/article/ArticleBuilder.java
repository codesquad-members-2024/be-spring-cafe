package codesquad.springcafe.article;

public class ArticleBuilder {

    private String articleId;
    private String author;
    private String title;
    private String content;

    public ArticleBuilder articleId(String articleId) {
        this.articleId = articleId;
        return this;
    }

    public ArticleBuilder author(String author) {
        this.author = author;
        return this;
    }

    public ArticleBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder content(String content) {
        this.content = content;
        return this;
    }

    public Article build() {
        return new Article(articleId, author, title, content);
    }

}
