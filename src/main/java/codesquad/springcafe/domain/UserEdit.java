package codesquad.springcafe.domain;

public class UserEdit {
    private final String name;
    private final String email;
    private final String password;
    private final String newPassword;

    public UserEdit(String name, String email, String password, String newPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
