package codesquad.springcafe.domain;

public class UserUpdate {
    private final String newNickname;
    private final String newEmail;
    private final String password;
    private final String newPassword;

    public UserUpdate(String newNickname, String newEmail, String password, String newPassword) {
        this.newNickname = newNickname;
        this.newEmail = newEmail;
        this.password = password;
        this.newPassword = newPassword;
    }
    public String getNewNickname() {
        return newNickname;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
