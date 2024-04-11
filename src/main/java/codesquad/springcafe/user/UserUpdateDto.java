package codesquad.springcafe.user;

public class UserUpdateDto {

    private String password;
    private String nickname;
    private String email;

    public UserUpdateDto(String password, String nickname, String email) {
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public User toEntity() {
        return new UserBuilder().password(password).nickname(nickname).email(email).build();
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
