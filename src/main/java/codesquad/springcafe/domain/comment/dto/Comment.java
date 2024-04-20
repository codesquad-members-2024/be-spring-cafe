package codesquad.springcafe.domain.comment.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Comment {
    private UUID identifier;
    private String writer;
    private UUID writtenArticle;
    private Timestamp createTime;
    private String contents;
    private int likeCount;

    public Comment(String identifier, String writer, String writtenArticle, Timestamp createTime, String contents, Integer likeCount) {
        if (identifier != null) this.identifier = UUID.fromString(identifier);
        else this.identifier = UUID.randomUUID();

        this.writer = writer;
        this.writtenArticle = UUID.fromString(writtenArticle);

        if (createTime != null )this.createTime = createTime;
        else this.createTime = Timestamp.valueOf(LocalDateTime.now());

        this.contents = contents;

        if (likeCount != null) this.likeCount = likeCount;
        else this.likeCount = 0;
    }

    public String getIdentifier() {
        return this.identifier.toString();
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getWrittenArticle() {
        return this.writtenArticle.toString();
    }

    public String getRoughCreateTime() {
        return this.createTime.toLocalDateTime().toString();
    }

    public String getCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createTime.toLocalDateTime().format(formatter);
    }
}
