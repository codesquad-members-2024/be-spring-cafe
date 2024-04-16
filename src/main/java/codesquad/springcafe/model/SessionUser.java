package codesquad.springcafe.model;

public class SessionUser {
    private final String userId;
    private final String userName;
    private final String userEmail;

    public SessionUser(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

}