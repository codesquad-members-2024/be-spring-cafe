package codesquad.springcafe.model;

public class LoginUser {
    private final String userId;
    private final String userPassword;

    public LoginUser(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
