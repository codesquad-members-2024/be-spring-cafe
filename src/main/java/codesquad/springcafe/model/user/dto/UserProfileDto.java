package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

import java.time.LocalDateTime;

public class UserProfileDto {

    private final String userId;
    private final String nickname;
    private final String email;
    private final LocalDateTime registerTime;

    public UserProfileDto(String userId, String nickname, String email, LocalDateTime registerTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.registerTime = registerTime;
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

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public User toEntity(){
        return new User(userId, nickname, email, registerTime);
    }
}
