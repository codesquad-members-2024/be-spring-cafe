package codesquad.springcafe.repository.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.repository.member.MemberRepositoryH2;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@JdbcTest
@Import(ArticleRepositoryH2.class)
class ArticleRepositoryH2Test {

    @Autowired
    private ArticleRepositoryH2 repository;

    @SpyBean
    private MemberRepositoryH2 memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("yelly", "123", null, null));
    }

    @AfterEach
    void reset_pk() {
        repository.clear();
        memberRepository.clear();
    }

    @DisplayName("yelly라는 작성자로 게시물을 최초로 추가하면 id는 1 이다")
    @Test
    void save() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));

        // when
        Article savedArticle = repository.save(article);

        // then
        assertThat(savedArticle.getId()).isEqualTo(1L);
        assertThat(savedArticle.getTitle()).isEqualTo("test");
        assertThat(savedArticle.getContents()).isEqualTo("test body");
        assertThat(savedArticle.getCreatedBy()).isEqualTo("yelly");
        assertThat(savedArticle.getCreatedAt()).isEqualTo(LocalDateTime.parse("2024-04-12T00:00:00"));
    }

    @DisplayName("제목이 test, 작성자가 yelly 인 최초 게시물을 찾을 수 있다")
    @Test
    void findById() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));

        repository.save(article);

        // when
        Optional<Article> optionalArticle = repository.findById(1L);

        // then
        assertThat(optionalArticle).isPresent();
        assertThat(optionalArticle.get().getId()).isEqualTo(1L);
        assertThat(optionalArticle.get().getTitle()).isEqualTo("test");
        assertThat(optionalArticle.get().getContents()).isEqualTo("test body");
        assertThat(optionalArticle.get().getCreatedBy()).isEqualTo("yelly");
        assertThat(optionalArticle.get().getCreatedAt()).isEqualTo(LocalDateTime.parse("2024-04-12T00:00:00"));
    }

    @DisplayName("10개 게시물을 모두 찾을 수 있다")
    @Test
    void findAll() {
        // given
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("test");
            article.setContents("test body");
            article.setCreatedBy("yelly");
            article.setCreatedAt(LocalDateTime.now());

            repository.save(article);
        }

        // when
        List<Article> articles = repository.findAll();

        // then
        assertThat(articles).hasSize(10);
        assertThat(articles).extracting("title")
                .contains("test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
    }

    @DisplayName("제목을 success title, 본문을 success contents 로 바꿀 수 있다")
    @Test
    void update() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));

        repository.save(article);

        UpdateArticle updateParam = new UpdateArticle();
        updateParam.setId(1L);
        updateParam.setCreatedBy("yelly");
        updateParam.setTitle("success title");
        updateParam.setContents("success contents");

        // when
        repository.update(updateParam);
        Article findArticle = repository.findById(1L).get();

        // then
        assertThat(findArticle.getTitle()).isEqualTo("success title");
        assertThat(findArticle.getCreatedBy()).isEqualTo("yelly");
        assertThat(findArticle.getContents()).isEqualTo("success contents");
    }

    @DisplayName("게시물을 soft delete 하면 deleted 상태가 true 가 되어 조회되지 않는다")
    @Test
    void softDelete() {
        // given
        Article article = new Article();
        article.setTitle("test");
        article.setContents("test body");
        article.setCreatedBy("yelly");
        article.setCreatedAt(LocalDateTime.parse("2024-04-12T00:00:00"));

        repository.save(article);

        // when
        repository.softDelete(1L);
        Optional<Article> optionalArticle = repository.findById(1L);

        // then
        assertThat(optionalArticle).isEmpty();
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

        repository.save(article);

        // when
        repository.restore(1L);
        Article findArticle = repository.findById(1L).get();

        // then
        assertThat(findArticle.isDeleted()).isFalse();
    }
}