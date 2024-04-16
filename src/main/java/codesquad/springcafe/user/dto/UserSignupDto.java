package codesquad.springcafe.user.dto;

import codesquad.springcafe.security.PasswordEncryptor;
import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserBuilder;

public class UserSignupDto {

    private String userId;
    private String email;
    private String nickname;
    private String password;

    public UserSignupDto(String userId, String email, String nickname, String password) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = PasswordEncryptor.encrypt(password);
    }

    public User toEntity() {
        return new UserBuilder().userId(userId).email(email).nickname(nickname).password(password)
            .build();
    }

    public String getUserId() {
        return userId;
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
