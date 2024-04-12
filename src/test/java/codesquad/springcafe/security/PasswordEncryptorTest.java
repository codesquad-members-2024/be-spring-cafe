package codesquad.springcafe.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordEncryptorTest {


    @Test
    @DisplayName("SHA256 방식으로 암호화가 되는지 테스트")
    void testPasswordEncrypt() {
        String password = "password";
        String encryptedPassword = PasswordEncryptor.encrypt(password);

        Assertions.assertThat(PasswordEncryptor.match(password, encryptedPassword)).isTrue();
    }
}