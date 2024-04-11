package codesquad.springcafe.Domain;

import java.time.LocalDate;

public class Article {
    private Long id;
    private String title;
    private String content;
    private LocalDate createdAt;

    // 생성자
    public Article() {
        this.createdAt = LocalDate.now();
    }

    // 게터와 세터
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }


    // toString 메서드
    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
