package codesquad.springcafe.article.domain;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Article {

    private UUID identifier;
    private String writer;
    private String title;
    private String contents;
    private final LocalDateTime createTime;

    public Article(String writer, String title, String contents) {
        this.writer = URLDecoder.decode(writer, StandardCharsets.UTF_8);
        this.title = title;
        this.contents = contents;
        this.createTime = LocalDateTime.now();
        this.identifier = UUID.randomUUID();
    }

    public String getIdentifier() {
        return this.identifier.toString();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createTime.format(formatter);
    }
}
