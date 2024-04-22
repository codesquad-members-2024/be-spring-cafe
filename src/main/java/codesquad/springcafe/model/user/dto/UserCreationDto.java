package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

import java.time.LocalDateTime;

public class UserCreationDto {
    private final String userId;
    private final String nickname;
    private final String password;
    private final String email;
    private final LocalDateTime registerTime;

    public UserCreationDto(String userId, String nickname, String password, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.registerTime = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public User toEntity() {
        return new User(userId, nickname, password, email, registerTime);
    }
}
