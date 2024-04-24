package codesquad.springcafe.dto;

import codesquad.springcafe.domain.Article;

public class ArticleCreateDto {

    private final String writer;
    private final String title;
    private final String content;

    public ArticleCreateDto(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article makeArticle() {
        return new Article(writer, title, content);
    }
}
