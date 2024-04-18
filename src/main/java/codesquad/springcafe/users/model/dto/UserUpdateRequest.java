package codesquad.springcafe.users.model.dto;

public class UserUpdateRequest {
    private final String newName;
    private final String newEmail;
    private final String currentPassword;
    private final String newPassword;

    public UserUpdateRequest(String newName, String newEmail, String currentPassword, String newPassword) {
        this.newName = newName;
        this.newEmail = newEmail;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getNewName() {
        return newName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
