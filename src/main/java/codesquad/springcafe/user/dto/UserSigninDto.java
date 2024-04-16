package codesquad.springcafe.user.dto;

public class UserSigninDto {

    private String userId;
    private String password;

    public UserSigninDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
