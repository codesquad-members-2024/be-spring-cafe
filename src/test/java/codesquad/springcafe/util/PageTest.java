package codesquad.springcafe.util;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.comment.Comment;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageTest {

    @DisplayName("현재 요청 페이지가 0 일 때 Page 는 첫 번째 Page 이다")
    @Test
    void isFirst() {
        // given
        List<Comment> comments = getComments();

        PageRequest pageRequest = new PageRequest(0, 15);

        int total = 200;

        // when
        Page<Comment> page = new Page<>(comments, pageRequest, total);

        // then
        assertThat(page.isFirst()).isTrue();
    }

    @DisplayName("전체 댓글이 200개 이면서 요청 사이즈는 15 이고 현재 요청 페이지가 14 페이지일 때 마지막 Page 이다")
    @Test
    void isLast() {
        // given
        List<Comment> comments = getComments();

        PageRequest pageRequest = new PageRequest(14, 15);

        int total = 200;

        // when
        Page<Comment> page = new Page<>(comments, pageRequest, total);

        // then
        assertThat(page.isLast()).isTrue();
    }

    @DisplayName("현재 Page 의 요청 페이지가 0일 때 이전 Page 는 없지만 다음 Page 는 존재한다")
    @Test
    void hasNext() {
        List<Comment> comments = getComments();

        PageRequest pageRequest = new PageRequest(0, 15);

        int total = 200;

        // when
        Page<Comment> page = new Page<>(comments, pageRequest, total);

        // then
        assertThat(page.hasPrevious()).isFalse();
        assertThat(page.hasNext()).isTrue();
    }

    @DisplayName("전체 페이지 개수가 14 페이지고, 현재 Page 의 요청 페이지가 14일 때 다음 Page 는 없지만 이존 Page 는 존재한다")
    @Test
    void hasPrevious() {
        List<Comment> comments = getComments();

        PageRequest pageRequest = new PageRequest(14, 15);

        int total = 200;

        // when
        Page<Comment> page = new Page<>(comments, pageRequest, total);

        // then
        assertThat(page.hasNext()).isFalse();
        assertThat(page.hasPrevious()).isTrue();
    }

    private List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comments.add(comment);
        }
        return comments;
    }
}