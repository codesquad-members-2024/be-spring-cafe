package codesquad.springcafe.reply;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyCreateDto {

    private String author;
    private String contents;
    private Long articleId;
    private Timestamp createdTime;
    private boolean deleted;

    public ReplyCreateDto(String author, String contents, Long articleId) {
        this.author = author;
        this.contents = contents;
        this.articleId = articleId;
        this.createdTime = Timestamp.valueOf(getCreatedTime());
        this.deleted = false;
    }

    public Reply toEntity() {
        return new ReplyBuilder().author(this.author).contents(this.contents)
            .articleId(this.articleId).createdTime(this.createdTime).deleted(this.deleted).build();
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }


    public Long getArticleId() {
        return articleId;
    }

    public String getCreatedTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean getDeleted() {
        return deleted;
    }
}
