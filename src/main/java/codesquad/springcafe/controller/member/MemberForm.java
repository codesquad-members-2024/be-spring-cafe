package codesquad.springcafe.controller.member;

public class MemberForm {

    private String loginId;
    private String password;
    private String userName;
    private String email;

    public String getLoginId() {
        return loginId;
    }

    public MemberForm setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MemberForm setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public MemberForm setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MemberForm setEmail(String email) {
        this.email = email;
        return this;
    }
}
