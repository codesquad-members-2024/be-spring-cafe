package codesquad.springcafe.dto;

public class UserLoginDto {
    private final String id;
    private final String password;

    public UserLoginDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
