package codesquad.springcafe.domain;

import codesquad.springcafe.dto.UserUpdateDto;

public class User {
    private String userId;
    private String nickname;
    private String email;
    private String password;

    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.nickname = userUpdateDto.getNickname();
        this.email = userUpdateDto.getEmail();
        this.password = userUpdateDto.getPassword();
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}