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

    public User(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.nickname = userDto.getNickname();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.created = LocalDateTime.now();
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.password = userUpdateDto.getPassword();
        this.nickname = userUpdateDto.getNickname();
        this.email = userUpdateDto.getEmail();
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                '}';
    }
}