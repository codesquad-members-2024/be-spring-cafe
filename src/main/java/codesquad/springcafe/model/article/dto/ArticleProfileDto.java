package codesquad.springcafe.model.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleProfileDto {
    private final long sequence;
    private final LocalDateTime publishTime;
    private final String writerNickname;
    private final String title;
    private final String content;
    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일 HH:mm";

    public ArticleProfileDto(long sequence, LocalDateTime publishTime, String writerNickname, String title, String content) {
        this.sequence = sequence;
        this.publishTime = publishTime;
        this.writerNickname = writerNickname;
        this.title = title;
        this.content = content;
    }

    public long getSequence() {
        return sequence;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public String getFormattedPublishTime(){
        return publishTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
