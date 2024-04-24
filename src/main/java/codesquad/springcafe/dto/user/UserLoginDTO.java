package codesquad.springcafe.dto.user;

public class UserLoginDTO {

    private final String userId;
    private final String password;

    public UserLoginDTO(String userId, String password) {
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
