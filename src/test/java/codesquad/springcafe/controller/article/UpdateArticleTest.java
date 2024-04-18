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

@SpringBootTest
class UpdateArticleTest {

    @Autowired
    private Validator validator;

    @DisplayName("게시물 번호, 작성자, 제목, 본문 내용 모두 값이 있으면 검증에 통과된다")
    @Test
    void validate_success() {
        // given
        UpdateArticle form = new UpdateArticle();
        form.setId(1L);
        form.setCreatedBy("yelly");
        form.setTitle("test title");
        form.setContents("test");

        // when
        Set<ConstraintViolation<UpdateArticle>> violations = validator.validate(form);

        Iterator<ConstraintViolation<UpdateArticle>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateArticle> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).isEmpty();
    }

    @DisplayName("제목이 비어있으면 검증에 실패하고 에러 메시지가 추가된다")
    @Test
    void validate_fail_when_empty_title() {
        // given
        /* 제목이 없는 경우 */
        UpdateArticle form = new UpdateArticle();
        form.setId(1L);
        form.setCreatedBy("yelly");
        form.setContents("test");

        // when
        Set<ConstraintViolation<UpdateArticle>> violations = validator.validate(form);

        Iterator<ConstraintViolation<UpdateArticle>> iterator = violations.iterator();

        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateArticle> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }
}