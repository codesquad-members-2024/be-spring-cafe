package codesquad.springcafe.user;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.security.PasswordEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("유저 객체의 비밀번호와 입력 비밀번호가 같은지 테스트")
    void checkPassword() {
        User user = new User("user", "email", "nickname", "password");
        String enteredPassword = PasswordEncryptor.encrypt("password");
        assertThat(PasswordEncryptor.encrypt("password")).isEqualTo(enteredPassword);
    }

}