package codesquad.springcafe.repository.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.article.Article;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleRepositoryInMemoryTest {

    private final ArticleRepository articleRepository = new ArticleRepositoryInMemory();

    @BeforeEach
    void clear() {
        articleRepository.clear();
    }

    @DisplayName("회원 저장에 성공할 떄마다 게시물 id는 1부터 1씩 증가한다.")
    @Test
    void save() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        LocalDateTime createdAt = LocalDateTime.parse("2024-04-10T00:00:00");

        Article article1 = makeArticle(title, createdBy, contents, createdAt);
        Article article2 = makeArticle(title, createdBy, contents, createdAt);

        // when
        Article savedArticle1 = articleRepository.save(article1);
        Article savedArticle2 = articleRepository.save(article2);

        // then
        assertThat(savedArticle1.getId()).isEqualTo(1L);
        assertThat(savedArticle2.getId()).isEqualTo(2L);
    }

    @DisplayName("2번째로 저장한 게시물의 아이디 2로 해당 게시물을 찾을 수 있다")
    @Test
    void findById() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        LocalDateTime createdAt = LocalDateTime.parse("2024-04-10T00:00:00");

        Article article1 = makeArticle(title, createdBy, contents, createdAt);
        Article article2 = makeArticle(title, createdBy, contents, createdAt);

        articleRepository.save(article1);
        articleRepository.save(article2);

        // when
        Optional<Article> optionalArticle = articleRepository.findById(2L);

        // then
        assertThat(optionalArticle).isPresent();
        assertThat(optionalArticle.get().getId()).isEqualTo(2L);
    }

    @DisplayName("저장된 게시물 2개를 모두 찾을 수 있다")
    @Test
    void findAll() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        LocalDateTime createdAt = LocalDateTime.parse("2024-04-10T00:00:00");

        Article article1 = makeArticle(title, createdBy, contents, createdAt);
        Article article2 = makeArticle(title, createdBy, contents, createdAt);

        articleRepository.save(article1);
        articleRepository.save(article2);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles.size()).isEqualTo(2);
        assertThat(articles).contains(article1, article2);
    }

    @DisplayName("저장된 게시물은 날짜를 기준으로 역순으로 정렬된다")
    @Test
    void findAll_reversed_order() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";

        Article article1 = makeArticle(title, createdBy, contents, LocalDateTime.parse("2024-04-10T00:00:00"));
        Article article2 = makeArticle(title, createdBy, contents, LocalDateTime.parse("2024-12-31T00:00:00"));

        articleRepository.save(article1);
        articleRepository.save(article2);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).containsExactly(article2, article1);
    }

    private Article makeArticle(String title, String createdBy, String contents, LocalDateTime createdAt) {
        Article article = new Article();
        article.setTitle(title);
        article.setCreatedBy(createdBy);
        article.setContents(contents);
        article.setCreatedAt(createdAt);

        return article;
    }
}