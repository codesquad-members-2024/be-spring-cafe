package codesquad.springcafe.article;

public class ArticleCreateDto {

    private String title;
    private String author;
    private String contents;

    public ArticleCreateDto(String title, String author, String contents) {
        this.title = title;
        this.author = author;
        this.contents = contents;
    }

    public Article toEntity() {
        return new ArticleBuilder().author(author).title(title).content(contents).build();
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
