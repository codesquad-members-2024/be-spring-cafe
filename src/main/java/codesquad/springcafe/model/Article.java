package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {
    private String writer;
    private String title;
    private String content;
    private Long id;
    private LocalDateTime writeDate;
    private long views;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0;
    }

    /**
     * 조회수를 증가시킵니다.
     */
    public void increaseViews() {
        views++;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(LocalDateTime writeDate) {
        this.writeDate = writeDate;
    }

    public long getViews() {
        return views;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Article article = (Article) object;
        return Objects.equals(writer, article.writer) && Objects.equals(title, article.title)
                && Objects.equals(content, article.content) && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, title, content, id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                ", writeDate=" + writeDate +
                ", views=" + views +
                '}';
    }
}
