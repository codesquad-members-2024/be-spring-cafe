package codesquad.springcafe.domain.user.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

/**
 * 유저 로그인에 필요한 데이터
 */
public class UserLoginData {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "올바른 이메일 형식이어야 합니다.")
    private final String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    @ConstructorProperties({"email", "password"})
    public UserLoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
