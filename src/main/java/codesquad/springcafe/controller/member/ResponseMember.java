package codesquad.springcafe.controller.member;

public class ResponseMember {
    private final String loginId;
    private final String userName;
    private final String email;

    public ResponseMember(String loginId, String userName, String email) {
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
