package codesquad.springcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
    private long replyId;
    private long articleId;
    private String writer;
    private String content;
    private boolean deleted;
    private LocalDateTime createdDate;

    public Reply(long replyId, long articleId, String writer, String content, boolean deleted,
                 LocalDateTime createdDate) {
        this.replyId = replyId;
        this.articleId = articleId;
        this.writer = writer;
        this.content = content;
        this.deleted = deleted;
        this.createdDate = createdDate;
    }

    public Reply(long articleId, String userId, String content) {
        this.articleId = articleId;
        this.writer = userId;
        this.content = content;
        this.deleted = false;
        this.createdDate = LocalDateTime.now();
    }

    public long getReplyId() {
        return replyId;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
