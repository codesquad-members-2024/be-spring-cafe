package codesquad.springcafe.controller.article;

import static org.assertj.core.api.Assertions.assertThat;

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
class ArticleFormTest {

    @Autowired
    private Validator validator;

    @DisplayName("제목, 본문 내용, 작성자 모두 값이 있으면 검증에 통과된다")
    @Test
    void validate_success() {
        // given
        String title = "test";
        String contents = "test body";
        String createdBy = "testUser";

        ArticleForm form = new ArticleForm();
        form.setTitle(title);
        form.setContents(contents);
        form.setCreatedBy(createdBy);

        // when
        Set<ConstraintViolation<ArticleForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<ArticleForm>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<ArticleForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).isEmpty();
    }

    @DisplayName("제목, 본문 내용, 작성자 중 하나라도 값이 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_title() {
        // given
        /* 제목이 없는 경우 */
        ArticleForm form = new ArticleForm();
        form.setContents("test");
        form.setCreatedBy("test");

        // when
        Set<ConstraintViolation<ArticleForm>> violations1 = validator.validate(form);

        Iterator<ConstraintViolation<ArticleForm>> iterator = violations1.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<ArticleForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("본문 값이 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_contents() {
        // given
        /* 본문이 없는 경우 */
        ArticleForm form = new ArticleForm();
        form.setTitle("test");
        form.setContents("test");

        // when
        Set<ConstraintViolation<ArticleForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<ArticleForm>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<ArticleForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("작성자 값이 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_createdBy() {
        // given
        /* 작성자가 없는 경우 */
        ArticleForm form = new ArticleForm();
        form.setTitle("test");
        form.setContents("test");

        // when
        Set<ConstraintViolation<ArticleForm>> violations = validator.validate(form);

        Iterator<ConstraintViolation<ArticleForm>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<ArticleForm> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }
}