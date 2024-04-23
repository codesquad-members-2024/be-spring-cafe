package codesquad.springcafe.article;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;

    public Article(Long id, String writer, String title, String contents, LocalDateTime createAt) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    // DB에서 꺼내올때만 사용
    public void setId(Long id) {
        this.id = id;
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
