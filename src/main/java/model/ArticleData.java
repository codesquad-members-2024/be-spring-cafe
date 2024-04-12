package model;

public class ArticleData {
    private final String userId;
    private final String title;
    private final String content;

    public ArticleData(String title, String content) {
        this.userId = "작성자";    // login 된 사용자를 리턴하도록 리팩토링 필요
        this.title = title;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
