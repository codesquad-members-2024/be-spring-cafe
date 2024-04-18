package codesquad.springcafe.user.domain;

public class LoginUser {

    private String userId;
    private String password;

    public LoginUser(String userId, String password) {
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
