package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserProfileDto {

    private final String userId;
    private final String nickname;
    private final String email;
    private final LocalDateTime registerTime;
    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일";

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

    public String getFormattedRegisterTime(){
        return registerTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }

    public User toEntity(){
        return new User(userId, nickname, email, registerTime);
    }
}
