package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private int id;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime time;
    private int views;

    // ArticleCreateDto를 통해 사용자가 입력한 Article을 저장할 때 사용
    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.time = LocalDateTime.now();
        this.views = 0;
    }

    // h2에서 Article 값을 가져올 때 사용하는 생성자
    public Article(Integer id, String userId, String title, String content, LocalDateTime time, int views) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.time = time;
        this.views = views;
    }

    public void setId(int id){
        this.id = id;
    }

    public void increaseViews(){
        this.views += 1;
    }

    public String getUserId(){
        return userId;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public int getViews(){
        return views;
    }

    public String getFormattedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

    public int getId(){
        return id;
    }


    @Override
    public String toString() {
        return "[id:" + id + "] " + "writer: " + userId + ", title: " + title + ", content: " + content;
    }

}
