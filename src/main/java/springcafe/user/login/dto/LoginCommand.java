package springcafe.user.login.dto;

import jakarta.validation.constraints.NotEmpty;

public class LoginCommand {

    @NotEmpty(message = "id를 입력하세요")
    private String userId;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;
    private boolean rememberUserId;

    public LoginCommand(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberUserId() {
        return rememberUserId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRememberUserId(boolean rememberUserId) {
        this.rememberUserId = rememberUserId;
    }
}
