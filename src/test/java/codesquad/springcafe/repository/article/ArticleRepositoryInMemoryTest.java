package codesquad.springcafe.repository.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.controller.article.UpdateArticle;
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

    @DisplayName("제목을 success title, 본문을 success contents 로 바꿀 수 있다")
    @Test
    void update() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        Article article = makeArticle(title, createdBy, contents, LocalDateTime.parse("2024-04-10T00:00:00"));
        articleRepository.save(article);

        UpdateArticle updateParam = new UpdateArticle();
        updateParam.setId(1L);
        updateParam.setCreatedBy("guest");
        updateParam.setTitle("success title");
        updateParam.setContents("success contents");

        // when
        articleRepository.update(updateParam);
        Article findArticle = articleRepository.findById(1L).get();

        // then
        assertThat(findArticle.getTitle()).isEqualTo("success title");
        assertThat(findArticle.getCreatedBy()).isEqualTo("guest");
        assertThat(findArticle.getContents()).isEqualTo("success contents");
    }

    @DisplayName("게시물을 soft delete 하면 deleted 상태가 true 가 된다")
    @Test
    void softDelete() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));

        articleRepository.save(article);

        // when
        articleRepository.softDelete(1L);
        Article findArticle = articleRepository.findById(1L).get();

        // then
        assertThat(findArticle.isDeleted()).isTrue();
    }

    @DisplayName("delete 상태인 게시물을 복원하면 deleted 상태가 false 가 된다")
    @Test
    void restore() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));
        article.softDelete();

        articleRepository.save(article);

        // when
        articleRepository.restore(1L);
        Article findArticle = articleRepository.findById(1L).get();

        // then
        assertThat(findArticle.isDeleted()).isFalse();
    }
}