package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRepositoryTest {

    ArticleRepository articleRepository = new ArticleRepository();

    @BeforeEach
    void init() {
        articleRepository.clear();
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다.")
    void saveArticle() {
        ArticleRequestDto articleRequestDto = new ArticleRequestDto("cori", "안녕하세요.", "오늘은 4월 11일 입니다.");
        Long articleId = articleRepository.save(articleRequestDto);

        Article findArticle = articleRepository.findById(articleId);

        assertThat(findArticle).usingRecursiveComparison().isEqualTo(new Article(articleId, articleRequestDto));
    }


}