package codesquad.springcafe.service.comment;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.repository.comment.CommentRepository;
import codesquad.springcafe.repository.member.MemberRepository;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {
    public static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        /* 테스터1, 테스터2 생성 */
        memberRepository.save(new Member("tester1", null, null, null));
        memberRepository.save(new Member("tester2", null, null, null));

        /* 게시물 생성 */
        Article article1 = makeArticle("test1", "tester1", "body1", NOW);
        Article article2 = makeArticle("test2", "tester2", "body2", NOW.minusDays(2));
        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @BeforeEach
    void clear() {
        memberRepository.clear();
        articleRepository.clear();
        commentRepository.clear();
    }

    @DisplayName("1번 게시물에 tester1 아이디를 가진 멤버가 '테스트 댓글'이라는 내용의 댓글을 게시할 수 있다")
    @Test
    void publish() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        // when
        Comment publishedComment = commentService.publish(comment);

        // then
        assertThat(publishedComment.getContent()).isEqualTo("테스트 댓글");
        assertThat(publishedComment.getCreatedBy()).isEqualTo("tester1");
        assertThat(publishedComment.getCreatedAt()).isEqualTo(NOW);
    }

    @DisplayName("1번 게시물에 작성한 '테스트 댓글' 이라는 내용의 댓글을 찾을 수 있다")
    @Test
    void findComment_success() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        commentService.publish(comment);

        // when
        Comment findComment = commentService.findComment(1L);

        // then
        assertThat(findComment.getContent()).isEqualTo("테스트 댓글");
        assertThat(findComment.getCreatedBy()).isEqualTo("tester1");
        assertThat(findComment.getCreatedAt()).isEqualTo(NOW);
    }

    @DisplayName("2번 게시물에 작성한 댓글을 찾을 수 없으면 ResourceNotFoundException 예외를 던진다")
    @Test
    void findComment_fail() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        commentService.publish(comment);

        // when & then
        assertThatThrownBy(() -> commentService.findComment(2L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("1번 게시물에 대한 모든 댓글 5개를 찾을 수 있다")
    @Test
    void findAllComment() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        for (int i = 0; i < 5; i++) {
            commentService.publish(comment);
        }

        // when
        List<Comment> comments = commentService.findAllComment(1L);

        // then
        assertThat(comments).hasSize(5);
        assertThat(comments).extracting("content")
                .contains("테스트 댓글", "테스트 댓글", "테스트 댓글", "테스트 댓글", "테스트 댓글");
    }

    @DisplayName("tester1 멤버가 작성한 댓글을 tester2 멤버가 수정 시도 시 UnauthorizedException 예외를 던진다")
    @Test
    void validateAuthor() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        commentService.publish(comment);

        // when & then
        assertThatThrownBy(() -> commentService.validateAuthor("tester2", "tester1"))
                .isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("tester1 멤버가 작성한 '테스트 댓글' 내용의 댓글을 동일 멤버가 '변경 후 댓글'로 수정할 수 있다")
    @Test
    void editComment_success() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        Comment publishedComment = commentService.publish(comment);

        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(publishedComment.getId());
        form.setAuthor("tester1");
        form.setContent("변경 후 댓글");

        // when
        commentService.editComment("tester1", form);
        Comment updatedComment = commentService.findComment(1L);

        // then
        assertThat(updatedComment.getContent()).isEqualTo("변경 후 댓글");
    }

    @DisplayName("tester1 멤버가 작성한 댓글을 tester2 멤버가 수정 시도 시 UnauthorizedException 예외를 던진다")
    @Test
    void editComment_fail() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        Comment publishedComment = commentService.publish(comment);

        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(publishedComment.getId());
        form.setAuthor("tester1");
        form.setContent("변경 후 댓글");

        // when & then
        assertThatThrownBy(() -> commentService.editComment("tester2", form))
                .isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("댓글 아이디 1번에 대해 삭제할 수 있다")
    @Test
    void unpublish() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setCreatedBy("tester1");
        comment.setContent("테스트 댓글");
        comment.setCreatedAt(NOW);

        commentService.publish(comment);

        // when
        commentService.unpublish(1L);

        // then
        assertThatThrownBy(() -> commentService.findComment(1L))
                .isInstanceOf(ResourceNotFoundException.class);
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