package codesquad.springcafe.dto;

import java.time.LocalDateTime;

public class ReplyForm {
    private String writerId;
    private String contents;
    private LocalDateTime time;
    private String articleId;

    public ReplyForm(String contents) {
        this.contents = contents;
        time = LocalDateTime.now();
    }

    public String getWriterId() {
        return writerId;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }
}
