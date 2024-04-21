package codesquad.springcafe.domain.comment;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private long id;
    private long articleId;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;
    private boolean deleted = false;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public long getArticleId() {
        return articleId;
    }

    public Comment setArticleId(long articleId) {
        this.articleId = articleId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Comment setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isSameAuthor(String author) {
        return createdBy.equals(author);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Comment setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Comment restore() {
        this.deleted = false;
        return this;
    }

    public Comment softDelete() {
        this.deleted = true;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment comment)) {
            return false;
        }
        return getId() == comment.getId() && getArticleId() == comment.getArticleId() && Objects.equals(getContent(),
                comment.getContent()) && Objects.equals(getCreatedBy(), comment.getCreatedBy())
                && Objects.equals(getCreatedAt(), comment.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArticleId(), getContent(), getCreatedBy(), getCreatedAt());
    }
}
