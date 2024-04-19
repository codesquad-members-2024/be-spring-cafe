package codesquad.springcafe.dto;

import codesquad.springcafe.domain.Article;

public class ArticleDto {
    private String title;
    private String content;
    private String writer;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.writer = "익명";
    }

    public Article toEntity() {
        return new Article(writer, title, content);
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}