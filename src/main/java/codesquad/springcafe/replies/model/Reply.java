package codesquad.springcafe.replies.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
    private long replyId;
    private final long articleId;
    private final String userId;
    private final String comment;
    private LocalDateTime creationDate;

    public Reply(long articleId, String userId, String comment) {
        this.articleId = articleId;
        this.userId = userId;
        this.comment = comment;
        this.creationDate = LocalDateTime.now();
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getReplyId() {
        return replyId;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.creationDate.format(formatter);
    }
}
