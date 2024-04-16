package codesquad.springcafe.dto;

import codesquad.springcafe.model.LoginUser;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {
    @NotBlank(message = "아이디를 입력하세요")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    private final String userPassword;

    public UserLoginDto(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public LoginUser creatLoginUser() {
        return new LoginUser(userId, userPassword);
    }
}
