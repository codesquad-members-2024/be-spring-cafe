package codesquad.springcafe.domain.comment;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @DisplayName("yelly 아이디를 가진 멤버는 첫 번째 게시물에 댓글을 만들 수 있다")
    @Test
    void create_comment() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        Comment comment = new Comment();

        // when
        comment.setArticleId(1L);
        comment.setCreatedBy("yelly");
        comment.setContent("댓글 테스트");
        comment.setCreatedAt(createdAt);

        // then
        assertThat(comment.getCreatedBy()).isEqualTo("yelly");
        assertThat(comment.getArticleId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("댓글 테스트");
        assertThat(comment.getCreatedAt()).isEqualTo(createdAt);
    }
}