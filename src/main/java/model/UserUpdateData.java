package model;

public class UserUpdateData {
    private final String newEmail;
    private final String currentPassword;
    private final String newPassword;

    public UserUpdateData(String newEmail, String currentPassword, String newPassword) {
        this.newEmail = newEmail;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
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
