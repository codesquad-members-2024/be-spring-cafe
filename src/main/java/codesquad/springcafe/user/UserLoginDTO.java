package codesquad.springcafe.user;

public class UserLoginDTO {
    private final String userid;
    private final String password;

    public UserLoginDTO(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public boolean isMatchedPwd(String inputPwd) {
        return this.password.equals(inputPwd);
    }
}
