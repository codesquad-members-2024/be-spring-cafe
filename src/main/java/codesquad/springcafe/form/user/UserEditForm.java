package codesquad.springcafe.form.user;

public class UserEditForm {
    private String email;
    private String nickname;
    private String currentPassword;
    private String newPassword;

    public UserEditForm(String email, String nickname, String currentPassword, String newPassword) {
        this.email = email;
        this.nickname = nickname;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }

}
