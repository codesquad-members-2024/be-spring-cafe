package codesquad.springcafe.dto;

import codesquad.springcafe.model.LoginUser;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String userPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public LoginUser creatLoginUser() {
        return new LoginUser(userId, userPassword);
    }
}
