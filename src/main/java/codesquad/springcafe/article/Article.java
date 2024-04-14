package codesquad.springcafe.article;

import codesquad.springcafe.user.DTO.SimpleUserInfo;

import java.sql.Timestamp;

public class Article {
    private int id;
    private Timestamp createdAt;
    private SimpleUserInfo author;
    private String title;
    private String content;
    private int point;
    private String authorId;

    public Article(int id, Timestamp createdAt, SimpleUserInfo simpleUserInfo, String title, String content, int point) {
        this.id = id;
        this.createdAt = createdAt;
        this.author = simpleUserInfo;
        this.title = title;
        this.content = content;
        this.point = point;
    }

    public void addPoint() {
        this.point += 1;
    }

    public int getId() {
        return id;
    }

    public SimpleUserInfo getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getPoint() {
        return point;
    }

    public String getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", created_datetime=" + createdAt +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", point=" + point +
                '}';
    }


}
