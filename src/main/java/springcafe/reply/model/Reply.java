package springcafe.reply.model;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private String content;
    private Long articleId;
    private String writer;
    private LocalDateTime createdDate;


    public Reply(String content, Long articleId, String writer) {
        this.content = content;
        this.articleId = articleId;
        this.writer = writer;
    }

    public Reply(Long id, String content, Long articleId, String writer, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.articleId = articleId;
        this.writer = writer;
        this.createdDate = createdDate;
    }

    public boolean matchesWriter(String writer){
        return this.writer.equals(writer);

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
