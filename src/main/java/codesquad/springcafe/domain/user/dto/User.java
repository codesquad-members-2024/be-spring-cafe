package codesquad.springcafe.domain.user.dto;


import java.time.LocalDateTime;

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

    public String getPassword() {
        return password;
    }

    public boolean isSamePassword(String password) {
        return this.password.contentEquals(password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(userId).append(",").append(password).append(",").append(email).append(",").append(name).append(",").append(getCrateTime()).append(")");

        return sb.toString();
    }
}
