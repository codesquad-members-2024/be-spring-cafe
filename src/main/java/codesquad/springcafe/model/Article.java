package codesquad.springcafe.model;

import codesquad.springcafe.dto.ArticleRequestDto;

public class Article {

    private final Long articleId;
    private final String writer;
    private final String title;
    private final String contents;

    public Article(Long articleId, String writer, String title, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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
}
