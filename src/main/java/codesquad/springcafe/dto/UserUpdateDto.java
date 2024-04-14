package codesquad.springcafe.dto;

import codesquad.springcafe.domain.User;

public class UserUpdateDto {
    private String password;
    private String newPassword;
    private String newNickname;
    private String newEmail;

    public UserUpdateDto(String password, String newPassword, String newNickname, String newEmail) {
        this.password = password;
        this.newPassword = newPassword;
        this.newNickname = newNickname;
        this.newEmail = newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public String getNewEmail() {
        return newEmail;
    }
}
