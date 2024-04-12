package codesquad.springcafe.domain;

public class User {
    private final String userId;
    private final String nickname;
    private final String email;
    private final String password;

    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "userId: " + userId + ", nickname: " + nickname + ", email: " + email;
    }


}
