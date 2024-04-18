package codesquad.springcafe.controller.login;

import jakarta.validation.constraints.NotEmpty;

public class loginForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public loginForm() {
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public loginForm setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public loginForm setPassword(String password) {
        this.password = password;
        return this;
    }
}
