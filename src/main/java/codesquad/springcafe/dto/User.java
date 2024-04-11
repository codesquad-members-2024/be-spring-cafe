package codesquad.springcafe.dto;

public class User {
    private static final String TO_STRING_FORMAT = "[사용자] %s, %s, %s, %s";

    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;

    public User() {

    }

    public User(long id, String userId, String userPw, String userName, String userEmail) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public String getUserPw() {
        return userPw;
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

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean matchPassword(String userPw) {
        return this.userPw.equals(userPw);
    }

    public void updateUser(UpdatedUser updatedUser) {
        this.userPw = updatedUser.getUserPw();
        this.userName = updatedUser.getUserName();
        this.userEmail = updatedUser.getUserEmail();
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, userId, userPw, userName, userEmail);
    }
}
