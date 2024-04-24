package codesquad.springcafe.dto;

public class UserDto {
    private String userId;
    private String nickname;
    private String email;
    private String password;

    public UserDto(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
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
