package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public class Article {
    private final String writer;
    private final String title;
    private final String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime writeDate;
    private boolean isDeleted;
    private long views;
    private Long id;

    public Article(String writer, String title, String content, LocalDateTime writeDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
    }

    /**
     * 조회수를 증가시킵니다.
     */
    public void increaseViews() {
        views++;
    }

    public Article update(String title, String content) {
        Article article = new Article(writer, title, content, writeDate);
        article.setId(id);
        article.setViews(views);
        return article;
    }

    public void delete() {
        isDeleted = true;
    }

    public boolean hasSameWriter(String writer) {
        return this.writer.equals(writer);
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public boolean isDeleted() {
        return isDeleted;
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
                ", writeDate=" + writeDate +
                ", isDeleted=" + isDeleted +
                ", views=" + views +
                ", id=" + id +
                '}';
    }
}
