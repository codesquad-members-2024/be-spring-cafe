package codesquad.springcafe.domain.article;

import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Article {
    private int id;
    private Timestamp createdAt;
    private SimpleUserInfo author;
    private String title;
    private String content;
    private int point;
    private String formedTime;

    public Article(int id, Timestamp createdAt, SimpleUserInfo simpleUserInfo, String title, String content, int point) {
        this.id = id;
        this.createdAt = createdAt;
        this.author = simpleUserInfo;
        this.title = title;
        this.content = content;
        this.point = point;
        this.formedTime = formedTime(createdAt);
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

    private String formedTime(Timestamp createdAt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.toLocalDateTime().format(formatter);
    }

    public String getFormedTime() {
        return formedTime;
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
