package codesquad.springcafe.reply;

import java.sql.Timestamp;

public class ReplyBuilder {

    private Long replyId;
    private String author;
    private String contents;
    private String userId;
    private Long articleId;
    private Timestamp createdTime;

    public ReplyBuilder replyId(Long replyId) {
        this.replyId = replyId;
        return this;
    }

    public ReplyBuilder author(String author) {
        this.author = author;
        return this;
    }

    public ReplyBuilder contents(String contents) {
        this.contents = contents;
        return this;
    }

    public ReplyBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public ReplyBuilder articleId(Long articleId) {
        this.articleId = articleId;
        return this;
    }

    public ReplyBuilder createdTime(Timestamp createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public Reply build() {
        return new Reply(replyId, author, contents, userId, articleId, createdTime);
    }

}
