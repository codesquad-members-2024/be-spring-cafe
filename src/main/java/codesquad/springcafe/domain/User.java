package codesquad.springcafe.domain;

public class User {
    private final String userId;
    private final String name;
    private final String email;
    private final String password;

    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "userId: " + userId + ", name: " + name + ", email: " + email;
    }
}