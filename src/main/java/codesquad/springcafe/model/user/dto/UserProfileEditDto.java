package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

public class UserProfileEditDto {
    private final String userId;
    private final String password;
    private final String nickname;
    private final String email;

    public UserProfileEditDto(String userId, String password, String nickname, String email) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public User toEntity(){
        return new User(userId, nickname, password, email);
    }
}
