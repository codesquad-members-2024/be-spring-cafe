package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
    private String contents;
    private String writerId;
    private boolean deleted;
    private Long articleId;
    private LocalDateTime time;
    private Long id;

    public Reply(String contents, String writerId, LocalDateTime time, boolean deleted, Long articleId) {
        this.contents = contents;
        this.writerId = writerId;
        this.deleted = deleted;
        this.time = time;
        this.articleId = articleId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long id) {
        this.articleId = articleId;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
