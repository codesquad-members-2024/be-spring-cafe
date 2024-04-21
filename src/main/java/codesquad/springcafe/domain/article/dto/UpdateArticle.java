package codesquad.springcafe.domain.article.dto;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UpdateArticle {

    private String writer;
    private String title;
    private String contents;
    private String identifier;

    public UpdateArticle(String writer, String title, String contents) {
        this.writer = URLDecoder.decode(writer, StandardCharsets.UTF_8);
        this.title = URLDecoder.decode(title, StandardCharsets.UTF_8);
        this.contents = URLDecoder.decode(contents, StandardCharsets.UTF_8);
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
