package codesquad.springcafe.dto;

import codesquad.springcafe.domain.Article;
import java.time.LocalDateTime;

public class ArticleDto {
    private String title;
    private String content;
    private String writer;
    private LocalDateTime localDateTime;
    private long views;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
        /* 나머지 필드 초기화 */
        this.writer = "익명";
        this.localDateTime = LocalDateTime.now();
        this.views = 0L;
    }

    public Article toEntity(Long articleId) {
        return new Article(articleId, writer, title, content, localDateTime, views);
    }
}