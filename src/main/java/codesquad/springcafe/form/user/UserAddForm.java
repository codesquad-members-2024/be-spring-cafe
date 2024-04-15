package codesquad.springcafe.form.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserAddForm {
    @NotBlank
    private String email;
    @Length(min = 2, max = 10)
    private String nickname;
    @Length(min = 3, max = 10)
    private String password;

    public UserAddForm(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
