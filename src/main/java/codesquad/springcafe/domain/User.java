package codesquad.springcafe.domain;

public class User {
    private final String userId;
    private final String email;
    private final String nickname;
    private final String password;

    public User(String userId, String email, String nickname, String password) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }
}
