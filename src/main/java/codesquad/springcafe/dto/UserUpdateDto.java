package codesquad.springcafe.dto;

public class UserUpdateDto {
    private String oldPassword;
    private String password;
    private String nickname;
    private String email;

    public UserUpdateDto(String oldPassword, String password, String nickname, String email) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
}
