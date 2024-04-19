package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Reply;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleInfoDTO {
    private final Long index;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String contents;
    private final List<Reply> replies;

    public ArticleInfoDTO(Long index, LocalDateTime timestamp, String writer, String title, String contents, List<Reply> replies) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.replies = replies;
    }

    public Long getIndex() {
        return index;
    }

    public String getFormattedTimeStamp() {
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

    public List<Reply> getReplies() {
        return replies;
    }

    public int getReplyCounts() {
        return replies.size();
    }
}