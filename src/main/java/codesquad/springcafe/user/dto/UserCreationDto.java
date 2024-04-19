package codesquad.springcafe.user.dto;

import codesquad.springcafe.user.User;

public class UserCreationDto {
    private String userId;
    private String password;
    private String name;
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

    public User toEntity(String userId) {
        return new User(userId, password, name, email);
    }
}
