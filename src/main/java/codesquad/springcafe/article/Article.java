package codesquad.springcafe.article;

import java.time.LocalDateTime;

public class Article {
    private final int id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;

    public Article(int id, String writer, String title, String contents, LocalDateTime createAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
