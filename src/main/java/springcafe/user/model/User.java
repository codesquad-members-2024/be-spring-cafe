package springcafe.user.model;

public class User {

    private final String userId;
    private String name;
    private String email;
    private String password;
    private Long id;


    public User(String userId, String name, String email,Long id) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.id =id;
    }

    public User(String userId, String name, String email,String password) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String userId, String name, String email, String password, Long Id) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = Id;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
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

    public Long getId() {
        return id;
    }
    public boolean matchUserId(String writer) {
        return this.userId.equals(writer);
    }
}
