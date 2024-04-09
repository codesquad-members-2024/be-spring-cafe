package model;

public class UserVO {
    private final String email;
    private final String userId;
    private final String password;

    public UserVO(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
