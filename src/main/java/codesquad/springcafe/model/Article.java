package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Article {
    private final Long index;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String contents;
    private final List<Reply> replies;

    @Autowired
    public Article(Long index, LocalDateTime timestamp, String writer, String title, String contents) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.replies = new ArrayList<>();
    }

    public Long getIndex() {
        return index;
    }

    public String getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
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
    public int getReplyCounts() {
        return replies.size();
    }
}