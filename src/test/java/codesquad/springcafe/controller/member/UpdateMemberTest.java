package codesquad.springcafe.controller.member;

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
class UpdateMemberTest {

    @Autowired
    private Validator validator;

    @DisplayName("로그인 아이디, 멤버 이름, 변경 전 패스워드, 변경 후 패스워드, 이메일 주소 모두 값이 있으면 검증에 통과된다")
    @Test
    void validate_success() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setBeforePassword("test");
        updateMember.setAfterPassword("test123");
        updateMember.setUserName("tester");
        updateMember.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).isEmpty();
    }

    @DisplayName("로그인 아이디 값이 없으면 검증에 실패한다")
    @Test
    void validate_fail_when_empty_loginId() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setBeforePassword("test");
        updateMember.setAfterPassword("test123");
        updateMember.setUserName("tester");
        updateMember.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("변경 전 패스워드 값이 없으면 검증에 실패한다")
    @Test
    void validate_fail_when_empty_before_password() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setAfterPassword("test123");
        updateMember.setUserName("tester");
        updateMember.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("변경 후 패스워드 값이 없으면 검증에 실패한다")
    @Test
    void validate_fail_when_empty_after_password() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setBeforePassword("test");
        updateMember.setUserName("tester");
        updateMember.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("닉네임 값이 없으면 검증에 실패한다")
    @Test
    void validate_fail_when_empty_user_name() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setBeforePassword("test");
        updateMember.setAfterPassword("test");
        updateMember.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("이메일 값이 없으면 검증에 실패한다")
    @Test
    void validate_fail_when_empty_email() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setBeforePassword("test");
        updateMember.setAfterPassword("test");
        updateMember.setUserName("tester");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("비어 있을 수 없습니다");
    }

    @DisplayName("이메일 형식이 아니면 검증에 실패한다")
    @Test
    void validate_fail_when_not_email_pattern() {
        // given
        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setBeforePassword("test");
        updateMember.setAfterPassword("test");
        updateMember.setUserName("tester");
        updateMember.setEmail("123");

        // when
        Set<ConstraintViolation<UpdateMember>> violations = validator.validate(updateMember);

        Iterator<ConstraintViolation<UpdateMember>> iterator = violations.iterator();
        List<String> message = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<UpdateMember> next = iterator.next();
            message.add(next.getMessage());
        }

        // then
        assertThat(message).contains("올바른 형식의 이메일 주소여야 합니다");
    }
}