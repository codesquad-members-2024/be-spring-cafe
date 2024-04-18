package codesquad.springcafe.domain.user.data;

import java.beans.ConstructorProperties;

public class UserUpdateRequest {
    private final String email;
    private final String name;

    @ConstructorProperties({"email", "name"})
    public UserUpdateRequest( String email, String name) {
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
