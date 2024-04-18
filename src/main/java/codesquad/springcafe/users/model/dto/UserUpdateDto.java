package codesquad.springcafe.users.model.dto;

public class UserUpdateDto {
    private final String newName;
    private final String newEmail;
    private final String newPassword;

    public UserUpdateDto(String newName, String newEmail, String newPassword) {
        this.newName = newName;
        this.newEmail = newEmail;
        this.newPassword = newPassword;
    }

    public String getNewName() {
        return newName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
