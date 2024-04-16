package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public class Comment {
    private final String writer;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final String content;
    private final Long articleId;
    private final LocalDateTime writeDate;
    private Long id;

    public Comment(String writer, String content, Long articleId, LocalDateTime writeDate) {
        this.writer = writer;
        this.content = content;
        this.articleId = articleId;
        this.writeDate = writeDate;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Comment comment = (Comment) object;
        return Objects.equals(writer, comment.writer) && Objects.equals(content, comment.content)
                && Objects.equals(articleId, comment.articleId) && Objects.equals(writeDate,
                comment.writeDate) && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, content, articleId, writeDate, id);
    }
}
