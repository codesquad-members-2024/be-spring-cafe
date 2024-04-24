package codesquad.springcafe.dto;

import codesquad.springcafe.domain.Article;

public class ArticleCreateDto {

    private final String title;
    private final String content;

    public ArticleCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article makeArticle(String userId) {
        return new Article(userId, title, content);
    }
}
