package codesquad.springcafe.domain.comment.DTO;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public record Comment(int id, int articleId, String content, Timestamp createdAt, String author, String authorId) {
    public String formedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.createdAt.toLocalDateTime().format(formatter);
    }
}
