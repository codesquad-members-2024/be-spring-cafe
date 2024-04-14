package codesquad.springcafe.domain.user.data;

import codesquad.springcafe.domain.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

/**
 * 유저 회원가입 정보를 저장하는 데이터 클래스
 */
public class UserJoinData {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "올바른 이메일 형식이어야 합니다.")
    private final String email;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    @ConstructorProperties({"email", "name", "password"})
    public UserJoinData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    /**
     * 저장된 속성 값을 활용해 User 객체를 생성합니다
     * <p>암호화된 비밀번호를 전달받습니다.
     * <p>생성시간과 수정시간은 현재로 설정합니다
     * @return User 객체 생성해 반환
     */
    public User toUser(String encodedPwd) {
        return new User(this.name, this.email, encodedPwd,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
