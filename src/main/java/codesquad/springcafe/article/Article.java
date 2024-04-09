package codesquad.springcafe.article;

import java.sql.Timestamp;

public class Article {
    private int id;
    private Timestamp created_datetime;
    private String author;
    private String title;
    private String content;
    private int point;

    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.point = 0;
    }

    public Article completeArticle(int id, Timestamp created_datetime, int point) {
        this.setId(id);
        this.setCreatedDateTime(created_datetime);
        this.setPoint(point);

        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDateTime(Timestamp created){
        this.created_datetime = created;
    }

    public void addPoint(){
        this.point +=1;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreated_datetime() {
        return created_datetime;
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
                ", created_datetime=" + created_datetime +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", point=" + point +
                '}';
    }
}
