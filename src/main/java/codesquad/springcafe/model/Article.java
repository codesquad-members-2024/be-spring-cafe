package codesquad.springcafe.model;

import codesquad.springcafe.dto.ArticleRequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private final Long articleId;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime localDateTime;
    private int hits;

    public Article(Long articleId, String writer, String title, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDateTime = LocalDateTime.now();
        this.hits = 0;
    }

    public Article(long articleId, ArticleRequestDto articleRequestDto) {
        this(articleId, articleRequestDto.getWriter(), articleRequestDto.getTitle(), articleRequestDto.getContents());
    }

    public Long getArticleId() {
        return articleId;
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

    public String getLocalDateTime() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public int getHits() {
        return hits;
    }

    public void increaseHits() {
        hits++;
    }
}
