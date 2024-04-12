package codesquad.springcafe.user;

public class UserBuilder {

    private String userId;
    private String email;
    private String nickname;
    private String password;

    public UserBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(userId, email, nickname, password);
    }

}
