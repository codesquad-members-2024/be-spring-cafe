package codesquad.springcafe.domain;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public boolean passwordIsIllegal() {
        return password == null || password.equals("");
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() { return this.email; }
}
