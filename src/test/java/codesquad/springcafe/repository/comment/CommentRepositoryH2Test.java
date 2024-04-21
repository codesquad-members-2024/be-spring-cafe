package codesquad.springcafe.repository.comment;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.repository.article.ArticleRepositoryH2;
import codesquad.springcafe.repository.member.MemberRepositoryH2;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
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
@Import(CommentRepositoryH2.class)
class CommentRepositoryH2Test {

    public static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private CommentRepositoryH2 commentRepository;

    @SpyBean
    private MemberRepositoryH2 memberRepository;

    @SpyBean
    private ArticleRepositoryH2 articleRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("yelly", "123", null, null));
        articleRepository.save(new Article().setCreatedBy("yelly").setCreatedAt(NOW));
    }

    @AfterEach
    void reset_pk() {
        memberRepository.clear();
        articleRepository.clear();
        commentRepository.clear();
    }

    @DisplayName("yelly 아이디를 가진 멤버가 1번 게시물에 대한 댓글을 작성할 수 있다")
    @Test
    void save() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(savedComment.getArticleId()).isEqualTo(1L);
        assertThat(savedComment.getCreatedBy()).isEqualTo("yelly");
        assertThat(savedComment.getContent()).isEqualTo("테스트 댓글");
        assertThat(savedComment.getCreatedAt()).isEqualTo(NOW);
    }

    @DisplayName("멤버 아이디 yelly로 1번 게시물에 대한 댓글을 작성할 수 있다")
    @Test
    void findById() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        commentRepository.save(comment);

        // when
        Optional<Comment> findComment = commentRepository.findById(1L);

        // then
        assertThat(findComment).isPresent();
        assertThat(findComment.get()).extracting("articleId").isEqualTo(1L);
        assertThat(findComment.get()).extracting("createdBy").isEqualTo("yelly");
        assertThat(findComment.get()).extracting("content").isEqualTo("테스트 댓글");
    }

    @DisplayName("1번 게시물에 대한 모든 댓글 2개를 찾을 수 있다")
    @Test
    void findAllByArticleId() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        commentRepository.save(comment);
        commentRepository.save(comment);

        // when
        List<Comment> comments = commentRepository.findAllByArticleId(1L);

        // then
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting("content").contains("테스트 댓글", "테스트 댓글");
    }

    @DisplayName("'테스트 댓글'이라는 댓글을 '변경 후 댓글'이라는 내용으로 변경할 수 있다")
    @Test
    void update() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        Comment savedComment = commentRepository.save(comment);

        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(savedComment.getId());
        form.setContent("변경 후 댓글");

        // when
        commentRepository.update(form);
        Comment updatedComment = commentRepository.findById(savedComment.getId()).get();

        // then
        assertThat(updatedComment.getContent()).isEqualTo("변경 후 댓글");
    }

    @DisplayName("1번 게시물에 대한 댓글 1개를 삭제할 수 있다")
    @Test
    void delete() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        commentRepository.save(comment);

        // when
        commentRepository.delete(1L);
        List<Comment> comments = commentRepository.findAllByArticleId(1L);

        // then
        assertThat(comments).hasSize(0);
    }

    @DisplayName("게시물을 soft delete 하면 deleted 상태가 true 가 되어 조회되지 않는다")
    @Test
    void softDelete() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        commentRepository.save(comment);

        // when
        commentRepository.softDelete(1L);
        Optional<Comment> optionalComment = commentRepository.findById(1L);

        // then
        assertThat(optionalComment).isEmpty();
    }

    @DisplayName("delete 상태인 게시물을 복원하면 deleted 상태가 false 가 된다")
    @Test
    void restore() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);
        comment.softDelete();

        commentRepository.save(comment);

        // when
        commentRepository.restore(1L);
        Comment findComment = commentRepository.findById(1L).get();

        // then
        assertThat(findComment.isDeleted()).isFalse();
    }

    @DisplayName("아아디 1번 부터 10번까지 벌크 연산으로 삭제할 수 있다")
    @Test
    void bulkSoftDelete() {
        // given
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setContent("테스트 댓글");
        comment.setCreatedBy("yelly");
        comment.setCreatedAt(NOW);

        for (int i = 0; i < 10; i++) {
            commentRepository.save(comment);
        }

        List<Long> commentIds = LongStream.rangeClosed(1L, 10L).boxed().toList();

        // when
        commentRepository.bulkSoftDelete(commentIds);
        List<Comment> comments = commentRepository.findAllByArticleId(1L);

        // then
        assertThat(comments).hasSize(0);
    }
}