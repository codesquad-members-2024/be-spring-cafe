package codesquad.springcafe.domain;

import jakarta.annotation.Priority;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private int id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime time;
    private int views;

    // ArticleCreateDto를 통해 사용자가 입력한 Article을 저장할 때 사용
    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.time = LocalDateTime.now();
    }

    // h2에서 Article 값을 가져올 때 사용하는 생성자
    public Article(Integer id, String writer, String title, String content, LocalDateTime time, int views) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.time = time;
        this.views = views;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getWriter(){
        return writer;
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
        return "[id:" + id + "] " + "writer: " + writer + ", title: " + title + ", content: " + content;
    }

}
