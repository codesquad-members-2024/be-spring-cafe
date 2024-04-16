package codesquad.springcafe.dto;

import codesquad.springcafe.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserJoinDto {
    @NotBlank(message = "아이디를 입력하세요")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    private final String userPassword;

    @NotBlank(message = "이름을 입력하세요")
    private final String userName;

    @NotBlank(message = "이메일을 입력하세요")
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "올바른 이메일 형식을 입력하세요.")
    private final String userEmail;

    public UserJoinDto(String userId, String userPassword, String userName, String userEmail) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User creatUser() {
        return new User(userId, userPassword, userName, userEmail);
    }
}
