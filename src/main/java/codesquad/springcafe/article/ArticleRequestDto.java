package codesquad.springcafe.article;

public class ArticleRequestDto {

    private String title;

    private String author;

    public ArticleRequestDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
