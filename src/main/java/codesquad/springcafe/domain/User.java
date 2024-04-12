package codesquad.springcafe.domain;

public class User {
    private final String userId;
    private String name;
    private String email;
    private String password;

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
    public String getPassword(){
        return password;
    }

    public void edit(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "userId: " + userId + ", name: " + name + ", email: " + email;
    }
}