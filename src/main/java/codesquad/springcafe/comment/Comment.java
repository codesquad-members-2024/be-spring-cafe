package codesquad.springcafe.comment;

import java.time.LocalDateTime;

public class Comment {
    private int articleId;
    private String writer;
    private String contents;
    private LocalDateTime createdAt;

    public Comment(int articleId, String writer, String contents, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
