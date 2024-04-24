package codesquad.springcafe.dto;

import codesquad.springcafe.domain.User;

public class UserCreateDto {
    private String userId;
    private String nickname;
    private String email;
    private String password;

    public UserCreateDto(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public User makeUser() {
        return new User(userId, nickname, email, password);
    }

}
