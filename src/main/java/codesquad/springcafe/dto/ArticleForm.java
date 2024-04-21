package codesquad.springcafe.dto;

import java.time.LocalDateTime;

public class ArticleForm {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time;


    public ArticleForm(String writer, String title, String contents){
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getTime(){
        return time;
    }
}