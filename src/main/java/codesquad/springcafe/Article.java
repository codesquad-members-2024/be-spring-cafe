package codesquad.springcafe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private final long sequence;
    private final String writer;
    private final LocalDateTime publishTime;
    private String title;
    private String content;
    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일 HH:mm";

    public Article(long sequence, String title, String content) {
        this.sequence = sequence;
        this.title = title;
        this.writer = "익명";
        this.content = content;
        this.publishTime = LocalDateTime.now();
    }

    public long getSequence() {
        return sequence;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedPublishTime(){
        return publishTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }
}
