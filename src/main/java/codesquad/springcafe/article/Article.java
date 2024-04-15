package codesquad.springcafe.article;

import java.sql.Timestamp;

public class Article {
    private int id;
    private Timestamp createdAt;
    private String author;
    private String title;
    private String content;
    private int point;

    public Article(int id, Timestamp createdAt, String author, String title, String content, int point) {
        this.id = id;
        this.createdAt = createdAt;
        this.author = author;
        this.title = title;
        this.content = content;
        this.point = point;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(Timestamp created){
        this.createdAt = created;
    }

    public void addPoint(){
        this.point +=1;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getAuthor() {
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

    public void setPoint(int point) {
        this.point = point;
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
