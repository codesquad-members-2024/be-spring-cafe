package codesquad.springcafe.domain;

import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime created;

    public User(String userId, String nickname, String email, String password, LocalDateTime created) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.created = created;
    }

    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.created = LocalDateTime.now();
    }

    public UserDto toDto() {
        return new UserDto(userId, nickname, email, password);
    }

    public void update(UserUpdateDto userUpdateDto) {
        if (!password.equals(userUpdateDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
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

    public String getCreated() {
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}