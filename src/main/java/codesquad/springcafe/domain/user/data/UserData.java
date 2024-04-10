package codesquad.springcafe.domain.user.data;

public class UserData {
    private final String email;
    private final String name;

    public UserData(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
