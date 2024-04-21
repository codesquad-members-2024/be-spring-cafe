package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleMemoryRepositoryTest {

    ArticleRepository articleRepository = new ArticleMemoryRepository();

    @BeforeEach
    void init() {
        articleRepository.clear();
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다.")
    void saveArticle() {
        Long articleId = 1L;
        Article article = new Article(articleId, "cori", "안녕하세요", "오늘은 날씨가 흐립니다.");
        articleRepository.save(article);

        Article findArticle = articleRepository.findById(articleId);

        assertThat(findArticle).usingRecursiveComparison().isEqualTo(article);
    }


}