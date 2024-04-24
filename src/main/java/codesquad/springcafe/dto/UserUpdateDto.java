package codesquad.springcafe.dto;

import codesquad.springcafe.model.UpdateUser;

public class UserUpdateDto {

    private final String password;
    private final String newPassword;
    private final String name;
    private final String email;

    public UserUpdateDto(String password, String newPassword, String name, String email) {
        this.password = password;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UpdateUser toEntity(String userId) {
        return new UpdateUser(userId, password, newPassword, name, email);
    }

    @Override
    public String toString() {
        return "UserUpdateDto{" +
                "password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
