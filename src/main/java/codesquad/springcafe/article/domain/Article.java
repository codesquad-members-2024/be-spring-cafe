package codesquad.springcafe.article.domain;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Article {

    private UUID identifier;
    private String writer;
    private String title;
    private String contents;
    private Timestamp createTime;

    public Article(String identifier, String writer, String title, String contents, Timestamp createTime) {
        this.writer = URLDecoder.decode(writer, StandardCharsets.UTF_8);
        this.title = title;
        this.contents = contents;

        if (createTime != null) this.createTime = createTime;
        else this.createTime = Timestamp.valueOf(LocalDateTime.now());

        if (identifier != null) this.identifier = UUID.fromString(identifier);
        else this.identifier = UUID.randomUUID();
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

    public String getRoughCrateTime() {
        return createTime.toString();
    }

    public String getCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createTime.toLocalDateTime().format(formatter);
    }
}
