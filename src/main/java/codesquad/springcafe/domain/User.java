package codesquad.springcafe.domain;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
