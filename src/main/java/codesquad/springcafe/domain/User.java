package codesquad.springcafe.domain;

import codesquad.springcafe.dto.UserDto;
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

    public UserDto toDto() {
        return new UserDto(userId, nickname, email, password);
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.password = userUpdateDto.getNewPassword();
        this.nickname = userUpdateDto.getNewNickname();
        this.email = userUpdateDto.getNewEmail();
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