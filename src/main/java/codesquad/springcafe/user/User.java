package codesquad.springcafe.user;

import java.util.Objects;

public class User {
    private final String userId;
    private final String password;
    private final String nickName;
    private final String email;

    public User(String userId, String password, String nickName, String email) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
    }

    public boolean has(String password) {
        return Objects.equals(this.password, password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + nickName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
