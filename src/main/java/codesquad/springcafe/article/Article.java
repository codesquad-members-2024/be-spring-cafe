package codesquad.springcafe.article;

import java.time.LocalDateTime;

public class Article {
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;

    public Article(String writer, String title, String contents, LocalDateTime createAt) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
