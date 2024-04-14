package codesquad.springcafe.domain.user.data;

import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

/**
 * 유저 로그인에 필요한 데이터
 */
public class UserLoginRequest {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private final String loginId;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    @ConstructorProperties({"userId", "password"})
    public UserLoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
