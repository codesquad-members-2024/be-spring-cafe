package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

public class UserLoginDto {
    private final String userId;
    private final String password;

    public UserLoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public User toEntity(){
        return new User(userId, password);
    }
}
