package codesquad.springcafe.model;

import codesquad.springcafe.dto.ArticleRequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class Article {

    private Long articleId;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime localDateTime;
    private AtomicLong hits;

    public Article(Long articleId, String writer, String title, String contents, LocalDateTime localDateTime, long hits) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDateTime = localDateTime;
        this.hits = new AtomicLong(hits);
    }

    public Article(Long articleId, String writer, String title, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDateTime = LocalDateTime.now();
        this.hits = new AtomicLong();
    }

    public Article(long articleId, String writer, ArticleRequestDto articleRequestDto) {
        this(articleId, writer, articleRequestDto.getTitle(), articleRequestDto.getContents());
    }

    public Article(String writer, ArticleRequestDto articleRequestDto) {
        this.writer = writer;
        this.title = articleRequestDto.getTitle();
        this.contents = articleRequestDto.getContents();
        this.localDateTime = LocalDateTime.now();
        this.hits = new AtomicLong();
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

    public String getCreatedTime() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getHits() {
        return hits.get();
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public boolean checkWriter(String userId) {
        return this.writer.equals(userId);
    }

}
