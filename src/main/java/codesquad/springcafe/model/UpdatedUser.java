package codesquad.springcafe.model;

public class UpdatedUser {
    private final String userPassword;
    private final String userName;
    private final String userEmail;

    public UpdatedUser(String userPassword, String userName, String userEmail) {
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserPw() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

}