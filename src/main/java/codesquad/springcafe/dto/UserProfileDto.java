package codesquad.springcafe.dto;

import codesquad.springcafe.model.User;

public class UserProfileDto {

    private final String userId;
    private final String name;
    private final String email;

    public UserProfileDto(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserProfileDto toDto(User user) {
        return new UserProfileDto(user.getUserId(), user.getName(), user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
