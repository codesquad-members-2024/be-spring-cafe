package codesquad.springcafe.reply;

import java.sql.Timestamp;

public class Reply {

    private Long replyId;
    private String author;
    private String contents;
    private String userId;
    private Long articleId;
    private Timestamp createdTime;

    public Reply(Long replyId, String author, String contents, String userId, Long articleId,
        Timestamp createdTime) {
        this.replyId = replyId;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
        this.articleId = articleId;
        this.createdTime = createdTime;
    }

    public Long getReplyId() {
        return replyId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public String getUserId() {
        return userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
