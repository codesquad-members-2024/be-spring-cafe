package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long sequence;
    private String writer;
    private LocalDateTime publishTime;
    private String title;
    private String content;
    private String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일 HH:mm";

    public Article() {
        this.writer = "익명";
        this.publishTime = LocalDateTime.now();
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer){
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedPublishTime(){
        return publishTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
}
