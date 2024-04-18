package codesquad.springcafe.user;

import codesquad.springcafe.security.PasswordEncryptor;

public class User {

    private String userId;
    private String email;
    private String nickname;
    private String password;

    public User(String userId, String email, String nickname, String password) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return PasswordEncryptor.match(password, this.password);
    }

    public void update(User user) {
        this.email = user.email;
        this.nickname = user.nickname;
        this.password = user.password;
    }

}
