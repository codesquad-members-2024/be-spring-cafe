package springcafe.model;

public class User {

    private final String id;
    private String password;
    private final String name;
    private final String email;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIdMatched(String id){
        return this.id.equals(id);
    }

    public String getId() {
        return id;
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
