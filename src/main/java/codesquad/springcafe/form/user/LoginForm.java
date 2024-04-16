package codesquad.springcafe.form.user;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;

    public LoginForm(String email, String password) {
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
