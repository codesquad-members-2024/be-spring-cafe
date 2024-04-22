package codesquad.springcafe.user.dto;

import codesquad.springcafe.user.User;
import jakarta.validation.constraints.NotEmpty;

public class UserCreationDto {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;

    public UserCreationDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
