package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public class Comment {
    private final String writer;
    private final String content;
    private final Long articleId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime writeDate;
    private boolean isDeleted;
    private Long id;

    public Comment(String writer, String content, Long articleId, LocalDateTime writeDate) {
        this.writer = writer;
        this.content = content;
        this.articleId = articleId;
        this.writeDate = writeDate;
    }

    public void delete() {
        isDeleted = true;
    }

    public boolean hasSameWriter(String writer) {
        return this.writer.equals(writer);
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

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Comment comment = (Comment) object;
        return isDeleted == comment.isDeleted && Objects.equals(writer, comment.writer)
                && Objects.equals(content, comment.content) && Objects.equals(articleId,
                comment.articleId) && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, content, articleId, isDeleted, id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", articleId=" + articleId +
                ", writeDate=" + writeDate +
                ", isDeleted=" + isDeleted +
                ", id=" + id +
                '}';
    }
}
