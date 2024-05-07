package codesquad.springcafe.comment;

import java.time.LocalDateTime;

public class CommentCreateDTO {

    private final String writer;
    private final String content;
    private final Long articleId;
    private final LocalDateTime createdTime;

    public CommentCreateDTO(String writer, String content, Long articleId) {
        this.writer = writer;
        this.content = content;
        this.articleId = articleId;
        this.createdTime = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
