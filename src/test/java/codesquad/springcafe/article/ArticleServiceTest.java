package codesquad.springcafe.article;

import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.article.DTO.ArticleWithComments;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

//    @Autowired
//    ArticleRepository articleRepository;
//
//    @AfterEach
//    void tearDown() {
//        articleRepository.deleteAll();
//    }

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
}