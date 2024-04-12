package codesquad.springcafe.user;

public class UserRequestDto {

    private String userId;
    private String email;
    private String nickname;

    public UserRequestDto(String userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
