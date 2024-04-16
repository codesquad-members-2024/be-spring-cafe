package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public class Article {
    private final String writer;
    private final String title;
    private final String content;
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    private LocalDateTime writeDate;
    private long views;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.writeDate = LocalDateTime.now();
        this.views = 0;
    }

    /**
     * 조회수를 증가시킵니다.
     */
    public void increaseViews() {
        views++;
    }

    public Article update(String title, String content) {
        Article article = new Article(writer, title, content);
        article.setId(id);
        article.setViews(views);
        article.setWriteDate(writeDate);
        return article;
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

    public void setWriteDate(LocalDateTime writeDate) {
        this.writeDate = writeDate;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
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
