package codesquad.springcafe.domain.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {
    @DisplayName("제목은 test, 작성자는 guest, 내용은 test body 인 article 객체를 생성할 수 있다")
    @Test
    void create() {
        // given
        Article article = new Article();

        // when
        article.setTitle("test");
        article.setCreatedBy("guest");
        article.setContents("test body");

        // then
        assertThat(article.getTitle()).isEqualTo("test");
        assertThat(article.getCreatedBy()).isEqualTo("guest");
        assertThat(article.getContents()).isEqualTo("test body");
        assertThat(article.isDeleted()).isFalse();
    }

    @DisplayName("같은 제목, 작성자, 본문 내용, 작성시간 이더라도 발급된 id가 다르면 다른 게시물이다")
    @Test
    void equals() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        LocalDateTime createdAt = LocalDateTime.parse("2024-04-10T00:00:00");

        // when
        Article article1 = new Article();
        article1.setTitle(title);
        article1.setCreatedBy(createdBy);
        article1.setContents(contents);
        article1.setCreatedAt(createdAt);
        article1.setId(1L);

        Article article2 = new Article();
        article2.setTitle(title);
        article2.setCreatedBy(createdBy);
        article2.setContents(contents);
        article2.setCreatedAt(createdAt);
        article2.setId(2L);

        // then
        assertThat(article1).isNotEqualTo(article2);
    }

    @DisplayName("게시글에 대해 soft delete 를 할 수 있다")
    @Test
    void softDelete() {
        // given
        Article article = new Article();

        // when
        article.softDelete();

        // then
        assertThat(article.isDeleted()).isTrue();
    }

    @DisplayName("게시글에 대해 삭제 상태를 복원할 수 있다")
    @Test
    void restore() {
        // given
        Article article = new Article();

        // when
        article.restore();

        // then
        assertThat(article.isDeleted()).isFalse();
    }
}