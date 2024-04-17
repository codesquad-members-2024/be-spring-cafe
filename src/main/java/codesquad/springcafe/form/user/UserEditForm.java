package codesquad.springcafe.form.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserEditForm {
    @NotBlank
    private String email;
    @Length(min = 2, max = 10)
    private String nickname;
    @Length(min = 3, max = 10)
    private String currentPassword;
    @Length(min = 3, max = 10)
    private String newPassword;

    public UserEditForm(String email, String nickname, String currentPassword, String newPassword) {
        this.email = email;
        this.nickname = nickname;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }

}
