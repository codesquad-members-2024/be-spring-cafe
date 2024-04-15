package springcafe.user.model;

public class User {

    private final String userId;
    private String name;
    private String email;
    private String password;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isIdMatched(String id) {
        return this.userId.equals(id);
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
