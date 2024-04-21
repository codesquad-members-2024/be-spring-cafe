package codesquad.springcafe.controller.comment;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.controller.article.UpdateArticle;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CommentUpdateFormTest {

    @Autowired
    private Validator validator;

    @DisplayName("게시물 번호, 작성자, 제목, 본문 내용 모두 값이 있으면 검증에 통과된다")
    @Test
    void validate_success() {
        // given
        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(1L);
        form.setArticleId(1L);
        form.setAuthor("tester1");
        form.setContent("test title");

        // when
        Set<ConstraintViolation<CommentUpdateForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<CommentUpdateForm>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<CommentUpdateForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).isEmpty();
    }

    @DisplayName("내용이 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_title() {
        // given
        /* 제목이 없는 경우 */
        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(1L);
        form.setArticleId(1L);
        form.setAuthor("tester1");

        // when
        Set<ConstraintViolation<CommentUpdateForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<CommentUpdateForm>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<CommentUpdateForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("작성자가 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_author() {
        // given
        /* 작성자가 없는 경우 */
        CommentUpdateForm form = new CommentUpdateForm();
        form.setId(1L);
        form.setArticleId(1L);
        form.setContent("test title");

        // when
        Set<ConstraintViolation<CommentUpdateForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<CommentUpdateForm>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<CommentUpdateForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }
}