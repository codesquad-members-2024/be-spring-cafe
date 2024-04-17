package codesquad.springcafe.article;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.repository.ArticleRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
    }

    @DisplayName("게시글을 저장한다")
    @Test
    void add() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");

        // when
        articleRepository.add(article, author);

        //then
        assertThat(articleRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("게시글을 id로 조회할 수 있다")
    @Test
    void findById() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");

        // when
        articleRepository.add(article, author);
        Article articleFind = articleRepository.findById(1);

        //then
        assertThat(articleFind.getTitle()).isEqualTo("제목");
        assertThat(articleFind.getAuthor().name()).isEqualTo("테스터");
    }

    @DisplayName("게시글을 유저 Id로 조회할 수 있다")
    @Test
    void findByUserId() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");

        // when
        articleRepository.add(article, author);
        List<Article> articleFind = articleRepository.findByUserId("tester");

        //then
        assertThat(articleFind.size()).isEqualTo(1);
        assertThat(articleFind.get(0).getTitle()).isEqualTo("제목");
    }
}
