package codesquad.springcafe.articles.model;

import java.time.LocalDate;

public class Reply {
    private long replyId;
    private final long articleId;
    private final String userId;
    private final String comment;
    private LocalDate creationDate;

    public Reply(long articleId, String userId, String comment) {
        this.articleId = articleId;
        this.userId = userId;
        this.comment = comment;
        this.creationDate = LocalDate.now();
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public void setCreationDate(LocalDate creationDate) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
