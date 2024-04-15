package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private String writer;
    private String title;
    private String content;
    private LocalDateTime time;
    private String formattedTime;
    private String id;


    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.time = LocalDateTime.now();
        setFormattedTime();
    }

    public void setFormattedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.formattedTime = time.format(formatter);
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

    public String getFormattedTime(){
        return formattedTime;
    }


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }


    @Override
    public String toString() {
        return "[id:" + id + "] " + "writer: " + writer + ", title: " + title + ", content: " + content;
    }
    
}
