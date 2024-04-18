package codesquad.springcafe.model;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

public class Article {
    private final Long index;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String contents;
    private final List<Reply> replies;

    public Article(Long index, LocalDateTime timestamp, String writer, String title, String contents) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.replies = new ArrayList<>();
    }

    public Article(Long index, LocalDateTime timestamp, String writer, String title, String contents, List<Reply> replies) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.replies = replies;
    }

    public Long getIndex() {
        return index;
    }

    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public ArticleInfoDTO toDTO() {
        return new ArticleInfoDTO(index, timestamp, writer, title, contents, replies);
    }
}