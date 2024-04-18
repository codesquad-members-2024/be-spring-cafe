package codesquad.springcafe.service.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.repository.member.MemberRepository;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ArticleServiceTest {

    public static final LocalDateTime NOW = LocalDateTime.now();
    public static final LocalDateTime MINUS_DAYS = NOW.minusDays(2);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        /* 테스터1, 테스터2 생성 */
        memberRepository.save(new Member("tester1", null, null, null));
        memberRepository.save(new Member("tester2", null, null, null));

        /* 게시물 생성 */
        Article article1 = makeArticle("test1", "tester1", "body1", NOW);
        Article article2 = makeArticle("test2", "tester2", "body2", MINUS_DAYS);
        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @BeforeEach
    void clear() {
        articleRepository.clear();
        memberRepository.clear();
    }

    @DisplayName("3번째로 작성하는 게시물은 id가 3번으로 게시물이 발행된다")
    @Test
    void publish() {
        // given
        memberRepository.save(new Member("guest", null, null, null));

        String title = "test";
        String createdBy = "guest";
        String contents = "test body";

        Article article = makeArticle(title, createdBy, contents, NOW);

        // when
        Article publishedArticle = articleService.publish(article);

        // then
        assertThat(publishedArticle.getId()).isEqualTo(3L);
        assertThat(publishedArticle.getTitle()).isEqualTo("test");
        assertThat(publishedArticle.getCreatedBy()).isEqualTo("guest");
        assertThat(publishedArticle.getContents()).isEqualTo("test body");
        assertThat(publishedArticle.getCreatedAt()).isEqualTo(NOW);
    }

    @DisplayName("현재 시각으로부터 2일 전 게시물로서 2번째로 발행된 게시물을 찾을 수 있다")
    @Test
    void findArticle() {
        // given & when
        Optional<Article> optionalArticle = articleService.findArticle(2L);

        // then
        assertThat(optionalArticle).isPresent();
        assertThat(optionalArticle.get()).extracting("title").isEqualTo("test2");
        assertThat(optionalArticle.get()).extracting("createdBy").isEqualTo("tester2");
        assertThat(optionalArticle.get()).extracting("contents").isEqualTo("body2");
        assertThat(optionalArticle.get()).extracting("createdAt").isEqualTo(MINUS_DAYS);
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
        assertThat(articles).extracting("createdAt").contains(MINUS_DAYS, NOW);
    }

    private Article makeArticle(String title, String createdBy, String contents, LocalDateTime createdAt) {
        Article article = new Article();
        article.setTitle(title);
        article.setCreatedBy(createdBy);
        article.setContents(contents);
        article.setCreatedAt(createdAt);

        return article;
    }

    @DisplayName("존재하지 않는 999번 게시물에 대해 ResourceNotFoundException 이 발생한다")
    @Test
    void validateExists() {
        assertThatThrownBy(() -> articleService.validateExists(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("존재하지 않는 게시물입니다. 게시물 아이디: 999");
    }

    @DisplayName("로그인 된 아이디와 작성자가 불일치하면 UnauthorizedException 이 발생한다")
    @Test
    void validateAuthor() {
        assertThatThrownBy(() -> articleService.validateAuthor("yelly", "ghost"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("작성자와 일치하지 않습니다. 로그인 아이디: yelly");
    }

    @DisplayName("tester1 멤버가 작성한 게시물의 제목을 success title, 본문을 success contents 로 게시물을 수정할 수 있다")
    @Test
    void edit_success() {
        UpdateArticle updateParam = new UpdateArticle();
        updateParam.setId(1L);
        updateParam.setCreatedBy("tester1");
        updateParam.setTitle("success title");
        updateParam.setContents("success contents");

        // when
        articleService.editArticle("tester1", updateParam);
        Article findArticle = articleService.findArticle(1L).get();

        // then
        assertThat(findArticle.getTitle()).isEqualTo("success title");
        assertThat(findArticle.getCreatedBy()).isEqualTo("tester1");
        assertThat(findArticle.getContents()).isEqualTo("success contents");
    }

    @DisplayName("tester1 멤버가 작성한 게시물을 tester2 멤버가 수정하려 하면 게시물 수정에 실패한다")
    @Test
    void edit_fail() {
        // given
        UpdateArticle updateParam = new UpdateArticle();
        updateParam.setId(1L);
        updateParam.setCreatedBy("tester1");
        updateParam.setTitle("fail title");
        updateParam.setContents("fail contents");

        // when
        assertThatThrownBy(() -> articleService.editArticle("tester2", updateParam))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("작성자와 일치하지 않습니다. 로그인 아이디: tester2");
    }

    @DisplayName("tester1 멤버가 작성한 1번 게시글을 지울 수 있다")
    @Test
    void unpublish_success() {
        // given
        String loginId = "tester1";

        // when & then
        assertThatCode(() -> articleService.unpublish(loginId, 1L))
                .doesNotThrowAnyException();
    }

    @DisplayName("tester1 멤버가 작성한 1번 게시글을 tester2 멤버가 지우려 시도하면 UnauthorizedException 예외가 발생한다")
    @Test
    void unpublish_fail() {
        // given
        String loginId = "tester2";

        // when & then
        assertThatThrownBy(() -> articleService.unpublish(loginId, 1L))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("작성자와 일치하지 않습니다. 로그인 아이디: tester2");
    }
}