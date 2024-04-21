package codesquad.springcafe.domain.article;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {
    private long id;
    private String title;
    private String contents;
    private String createdBy;
    private LocalDateTime createdAt;
    private boolean deleted = false;

    public Article() {
    }

    public long getId() {
        return id;
    }

    public Article setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Article setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Article setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Article setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Article restore() {
        this.deleted = false;
        return this;
    }

    public Article softDelete() {
        this.deleted = true;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return getId() == article.getId() && Objects.equals(getTitle(), article.getTitle())
                && Objects.equals(getContents(), article.getContents()) && Objects.equals(
                getCreatedBy(), article.getCreatedBy()) && Objects.equals(getCreatedAt(), article.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContents(), getCreatedBy(), getCreatedAt());
    }
}
