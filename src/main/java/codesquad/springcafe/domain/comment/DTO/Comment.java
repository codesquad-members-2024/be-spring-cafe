package codesquad.springcafe.domain.comment.DTO;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Comment {
    private final int id;
    private final int articleId;
    private final String content;
    private final Timestamp createdAt;
    private final String author;
    private final String authorId;
    private final String formedTime;

    public Comment(int id, int articleId, String content, Timestamp createdAt, String author, String authorId) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.authorId = authorId;
        this.formedTime = covertFormedTime(createdAt);
    }

    private String covertFormedTime(Timestamp createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.toLocalDateTime().format(formatter);
    }
    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getFormedTime() {
        return formedTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Comment) obj;
        return this.id == that.id &&
                this.articleId == that.articleId &&
                Objects.equals(this.content, that.content) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.author, that.author) &&
                Objects.equals(this.authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, content, createdAt, author, authorId);
    }

    @Override
    public String toString() {
        return "Comment[" +
                "id=" + id + ", " +
                "articleId=" + articleId + ", " +
                "content=" + content + ", " +
                "createdAt=" + createdAt + ", " +
                "author=" + author + ", " +
                "authorId=" + authorId + ']';
    }

}
