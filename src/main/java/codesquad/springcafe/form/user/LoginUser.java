package codesquad.springcafe.form.user;

public class LoginUser {
    private String nickname;

    public LoginUser(String nickname) {
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
