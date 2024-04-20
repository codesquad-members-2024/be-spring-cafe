package codesquad.springcafe.article;

import codesquad.springcafe.domain.article.ArticleService;
import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.DTO.ArticleWithComments;
import codesquad.springcafe.domain.article.repository.ArticleRepository;
import codesquad.springcafe.domain.comment.CommentService;
import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    @Qualifier("testRepo")
    ArticleRepository articleRepository;
    @Autowired
    @Qualifier("testRepo")
    CommentRepository commentRepository;

    CommentService commentService;
    ArticleService articleService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository);
        articleService = new ArticleService(articleRepository, commentService);
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

    @DisplayName("게시글과 게시글에 달린 댓글을 조회할 수 있다")
    @Test
    void getArticle() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        // when
        ArticleWithComments get = articleService.getArticle(1);

        //then
        assertThat(get.article().getTitle()).isEqualTo("제목");
        assertThat(get.comments().size()).isEqualTo(0);
    }

    @DisplayName("존재하지 않는 게시글을 조회하면 예외를 던진다")
    @Test
    void getNotFoundArticle() {
        // given
        // when
        //then
        assertThatThrownBy(()->articleService.getArticle(1)).isInstanceOf(NotFoundException.class);
        assertThatThrownBy(()->articleService.getArticle(1)).hasMessage("요청하신 페이지를 찾을 수 없습니다.");
    }

    @DisplayName("전달받은 유저 정보와 작성자가 일치한다면 수정 가능하다")
    @Test
    void canModify() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        // when
        boolean result = articleService.canModify(1, author);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("전달받은 유저 정보와 작성자가 일치하지 않는다면 수정 불가하다")
    @Test
    void canNotModify() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        // when
        SimpleUserInfo author2 = new SimpleUserInfo("tester22", "테스터22");
        boolean result = articleService.canModify(1, author2);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("전달받은 유저 정보와 작성자가 일치하면 삭제 가능하다")
    @Test
    void canDelete() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        // when
        boolean result = articleService.canDelete(1, author);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("전달받은 유저 정보와 작성자가 일치하지 않는다면 삭제 불가하다")
    @Test
    void canNotDelete() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        // when
        SimpleUserInfo author2 = new SimpleUserInfo("tester22", "테스터22");
        boolean result = articleService.canDelete(1, author2);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("게시글의 다른 유저의 댓글이 존재하면 삭제 불가하다")
    @Test
    void canNotDeleteWithOthersComment() {
        // given
        ArticlePostReq article = new ArticlePostReq("제목", "내용");
        SimpleUserInfo author = new SimpleUserInfo("tester", "테스터");
        articleService.postArticle(article, author);

        SimpleUserInfo author2 = new SimpleUserInfo("tester22", "테스터22");
        CommentPostReq comment = new CommentPostReq(1, "댓글");
        commentService.postComment(comment , author2);

        // when
        boolean result = articleService.canDelete(1, author);

        //then
        assertThat(result).isFalse();
    }
}