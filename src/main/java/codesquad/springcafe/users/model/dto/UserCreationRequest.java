package codesquad.springcafe.users.model.dto;

public class UserCreationRequest {
    private final String userId;
    private final String email;
    private final String name;
    private final String password;

    public UserCreationRequest(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
