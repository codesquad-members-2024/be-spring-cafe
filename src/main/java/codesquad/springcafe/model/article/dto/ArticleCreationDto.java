package codesquad.springcafe.model.article.dto;

import codesquad.springcafe.model.article.Article;

import java.time.LocalDateTime;

public class ArticleCreationDto {
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime publishTime;

    public ArticleCreationDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.writer = "익명";
        this.publishTime = LocalDateTime.now();
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

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public Article toEntity(){
        return new Article(writer, publishTime, title, content);
    }
}
