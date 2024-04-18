package codesquad.springcafe.article;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleCreateDtoTest {

    ArticleCreateDto articleCreateDto;

    @BeforeEach
    void setUp() {
        articleCreateDto = new ArticleCreateDto("제목", "작성자", "내용", "userId");
    }

    @Test
    @DisplayName("ArticleCreateDto 인스턴스 생성 테스트")
    void testCreateDtoInstance() {
        assertThat(articleCreateDto.getTitle()).isEqualTo("제목");
        assertThat(articleCreateDto.getAuthor()).isEqualTo("작성자");
        assertThat(articleCreateDto.getContents()).isEqualTo("내용");

    }

    @Test
    @DisplayName("ArticleCreateDto toEntity 메서드 테스트")
    void testToEntity() {
        Article article = articleCreateDto.toEntity();
        assertThat(article.getTitle()).isEqualTo("제목");
        assertThat(article.getAuthor()).isEqualTo("작성자");
        assertThat(article.getContents()).isEqualTo("내용");
    }


}