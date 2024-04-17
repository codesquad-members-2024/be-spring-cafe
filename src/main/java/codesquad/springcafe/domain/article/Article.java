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

    public String formedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.createdAt.toLocalDateTime().format(formatter);
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
