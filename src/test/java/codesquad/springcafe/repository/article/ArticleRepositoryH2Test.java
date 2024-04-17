package codesquad.springcafe.repository.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.article.Article;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(ArticleRepositoryH2.class)
class ArticleRepositoryH2Test {

    @Autowired
    private ArticleRepositoryH2 repository;

    @AfterEach
    void reset_pk() {
        repository.clear();
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
}