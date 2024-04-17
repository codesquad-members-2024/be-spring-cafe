package codesquad.springcafe.article;

import codesquad.springcafe.domain.article.ArticleService;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.repository.ArticleRepository;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    @Qualifier("testRepo")
    ArticleRepository articleRepository;
    @Autowired
    @Qualifier("testRepo")
    CommentRepository commentRepository;

    ArticleService articleService;

    @BeforeEach
    void setUp(){
        articleService = new ArticleService(articleRepository, commentRepository);
    }

    @Test
    @DisplayName("비로그인 상태에서 게시글을 등록하면 익명으로 등록된다")
    void postArticle() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = null;
        
        // when
        articleService.postArticle(article, author);
        
        // then
        assertThat(articleService.getArticle(1).article().getTitle()).isEqualTo("제목");
        assertThat(articleService.getArticle(1).article().getAuthor().name()).isEqualTo("익명");
    }

    @Test
    @DisplayName("로그인 상태에서 게시글을 등록하면 닉네임으로 등록된다")
    void postArticleLoggedIn() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");

        // when
        articleService.postArticle(article, author);

        // then
        assertThat(articleService.getArticle(1).article().getTitle()).isEqualTo("제목");
        assertThat(articleService.getArticle(1).article().getAuthor().name()).isEqualTo("테스터");
    }
}