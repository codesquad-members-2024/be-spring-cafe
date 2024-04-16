package springcafe.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserCreateForm {

    @Size(min =3, max =25)
    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private String password;
    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;
    @NotEmpty(message = "이름은 필수 항목입니다.")
    private String name;
    @NotEmpty(message = "이메일을 필수 항목입니다.")
    @Email
    private String email;


    public UserCreateForm(String userId, String password, String passwordConfirm, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
