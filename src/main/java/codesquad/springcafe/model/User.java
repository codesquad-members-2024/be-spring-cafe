package codesquad.springcafe.model;

public class User {
    private static final String TO_STRING_FORMAT = "[사용자] %s, %s, %s, %s";

    private long id;
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;

    public User(String userId, String userPassword, String userName, String userEmail) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public User(long id, String userId, String userPassword, String userName, String userEmail) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public String getUserPassword() {
        return userPassword;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean matchPassword(String userPw) {
        return this.userPassword.equals(userPw);
    }

    public boolean matchUser(LoginUser loginUser) {
        return this.userId.equals(loginUser.getUserId()) && this.userPassword.equals(loginUser.getUserPassword());
    }

    public void updateUser(UpdatedUser updatedUser) {
        this.userPassword = updatedUser.getUserPw();
        this.userName = updatedUser.getUserName();
        this.userEmail = updatedUser.getUserEmail();
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, userId, userPassword, userName, userEmail);
    }
}
