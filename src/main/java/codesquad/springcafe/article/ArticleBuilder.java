package codesquad.springcafe.article;

public class ArticleBuilder {

    private Long articleId;
    private String author;
    private String title;
    private String contents;

    public ArticleBuilder articleId(Long articleId) {
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

    public ArticleBuilder contents(String contents) {
        this.contents = contents;
        return this;
    }

    public Article build() {
        return new Article(articleId, title, author, contents);
    }

}
