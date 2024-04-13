package codesquad.springcafe.dto;

import codesquad.springcafe.domain.User;

public class UserDto {
    private String userId;
    private String nickname;
    private String email;

    public UserDto(String userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getUserId(), user.getNickname(), user.getEmail());
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

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
