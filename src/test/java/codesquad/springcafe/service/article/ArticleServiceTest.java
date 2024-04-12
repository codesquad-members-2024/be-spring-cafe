package codesquad.springcafe.service.article;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        Article article1 = makeArticle("test1", "tester1", "body1", LocalDateTime.parse("2024-04-10T00:00:00"));
        Article article2 = makeArticle("test2", "tester2", "body2", LocalDateTime.parse("2024-12-31T00:00:00"));
        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @AfterEach
    void clear() {
        articleRepository.clear();
    }

    @DisplayName("3번째로 작성하는 게시물은 id가 3번으로 게시물이 발행된다")
    @Test
    void publish() {
        // given
        String title = "test";
        String createdBy = "guest";
        String contents = "test body";
        LocalDateTime createdAt = LocalDateTime.parse("2024-04-10T00:00:00");

        Article article = makeArticle(title, createdBy, contents, createdAt);

        // when
        Article publishedArticle = articleService.publish(article);

        // then
        assertThat(publishedArticle.getId()).isEqualTo(3L);
        assertThat(publishedArticle.getTitle()).isEqualTo("test");
        assertThat(publishedArticle.getCreatedBy()).isEqualTo("guest");
        assertThat(publishedArticle.getContents()).isEqualTo("test body");
        assertThat(publishedArticle.getCreatedAt()).isEqualTo(LocalDateTime.parse("2024-04-10T00:00:00"));
    }

    @DisplayName("2024-12-31 00:00:00에 2번째로 발행된 게시물을 찾을 수 있다")
    @Test
    void findArticle() {
        // given & when
        Optional<Article> optionalArticle = articleService.findArticle(2L);

        // then
        assertThat(optionalArticle).isPresent();
        assertThat(optionalArticle.get()).extracting("title").isEqualTo("test2");
        assertThat(optionalArticle.get()).extracting("createdBy").isEqualTo("tester2");
        assertThat(optionalArticle.get()).extracting("contents").isEqualTo("body2");
        assertThat(optionalArticle.get()).extracting("createdAt").isEqualTo(LocalDateTime.parse("2024-12-31T00:00:00"));
    }

    @DisplayName("발행된 모든 게시물 2개를 발행 시각 기준으로 역순으로 찾을 수 있다")
    @Test
    void findAllArticle() {
        // given & when
        List<Article> articles = articleService.findAllArticle();

        // then
        assertThat(articles.size()).isEqualTo(2);
        assertThat(articles).extracting("title").contains("test1", "test2");
        assertThat(articles).extracting("createdBy").contains("tester1", "tester2");
        assertThat(articles).extracting("contents").contains("body1", "body2");
        assertThat(articles).extracting("createdAt").contains(
                LocalDateTime.parse("2024-04-10T00:00:00"),
                LocalDateTime.parse("2024-12-31T00:00:00")
        );
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