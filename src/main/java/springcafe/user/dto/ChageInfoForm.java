package springcafe.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class ChageInfoForm {

    @NotEmpty(message = "현재 비밀번호를 정확하게 입력해야 정보를 변경할 수 있습니다.")
    String password;
    @NotEmpty(message = "이름은 필수 항목입니다.")
    String name;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "올바른 이메일 주소를 입력해야 합니다.")
    @NotEmpty(message = "이메일을 필수 항목입니다.")
    String email;

    public ChageInfoForm(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
