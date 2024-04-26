package codesquad.springcafe.dto.reply;

import codesquad.springcafe.model.Reply;
import java.time.LocalDateTime;

public class ReplyUploadDTO {

    private final Long articleId;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String content;

    public ReplyUploadDTO(Long articleId, String writer, String content) {
        this.articleId = articleId;
        this.timestamp = LocalDateTime.now();
        this.writer = writer;
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Reply toReply(Long index) {
        return new Reply(articleId, index, timestamp, writer, content);
    }
}