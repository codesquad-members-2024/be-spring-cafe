package codesquad.springcafe.user.domain;


import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private String userId;
    private String email;
    private String name;
    private String password;
    private final LocalDateTime crateTime;

    public User(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.crateTime = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCrateTime() {
        return crateTime.toString();
    }

    public boolean isSamePassword(String password) {
        return this.password.contentEquals(password);
    }
}
