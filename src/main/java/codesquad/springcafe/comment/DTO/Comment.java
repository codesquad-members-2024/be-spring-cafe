package codesquad.springcafe.comment.DTO;

import java.sql.Timestamp;

public record Comment(int id, int articleId, String content, Timestamp createdAt, String author, String authorId) {
}
