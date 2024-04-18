package codesquad.springcafe.article;

public class ArticleCreateDto {

    private String title;
    private String author;
    private String contents;
    private String userId;

    public ArticleCreateDto(String title, String author, String contents, String userId) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
    }

    public Article toEntity() {
        return new ArticleBuilder().author(author).title(title)
            .contents(contents).userId(userId).build();
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
}
