package codesquad.springcafe.article;

public class Article {

    private String title;
    private String author;
    private String contents;

    public Article(String title, String author, String contents) {
        this.title = title;
        this.author = author;
        this.contents = contents;
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
