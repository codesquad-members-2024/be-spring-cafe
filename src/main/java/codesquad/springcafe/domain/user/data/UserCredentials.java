package codesquad.springcafe.domain.user.data;

public class UserCredentials {
    private final Long userId;
    private final String username;

    public UserCredentials(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
