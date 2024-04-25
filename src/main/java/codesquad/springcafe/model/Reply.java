package codesquad.springcafe.model;

import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import java.time.LocalDateTime;

public class Reply implements Comparable<Reply> {
    private final Long articleId;
    private final Long index;
    private final String writer;
    private final LocalDateTime timestamp;
    private final String content;


    public Reply(Long articleId, Long index, String writer, LocalDateTime timestamp, String content) {
        this.articleId = articleId;
        this.index = index;
        this.writer = writer;
        this.timestamp = timestamp;
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Long getIndex() {
        return index;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public boolean isRightInfo(Long articleId) {
        return this.articleId.equals(articleId);
    }

    public boolean isRightInfo(Long articleId, Long index) {
        return this.articleId.equals(articleId) && this.index.equals(index);
    }

    public ReplyInfoDTO toDTO() {
        return new ReplyInfoDTO(articleId, index, writer, timestamp, content);
    }

    @Override
    public boolean equals(Object target) {
        if (target instanceof Reply targetReply) {
            return isRightInfo(targetReply.articleId, targetReply.index);
        }
        return false;
    }

    @Override
    public int compareTo(Reply other) {
        return Long.compare(index, other.index);
    }
}
