package codesquad.springcafe.article;

public class ArticleBuilder {

    private String author;
    private String title;
    private String content;

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
        return new Article(author, title, content);
    }

}
