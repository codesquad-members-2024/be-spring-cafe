package codesquad.springcafe.model.article;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Article {
    private final AtomicLong sequenceFactory = new AtomicLong();
    private long sequence;
    private String writer;
    private LocalDateTime publishTime;
    private String title;
    private String content;

    public Article() {
        this.writer = "익명";
        this.publishTime = LocalDateTime.now();
    }

    public Article(LocalDateTime publishTime, String title, String content) {
        this.writer = "익명";
        this.publishTime = publishTime;
        this.title = title;
        this.content = content;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(){
        this.sequence = sequenceFactory.incrementAndGet();
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

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
}
