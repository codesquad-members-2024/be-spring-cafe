package codesquad.springcafe.dto;

public class User {
    private static final String TO_STRING_FORMAT = "[사용자] %s, %s, %s, %s";

    private Long id; // TODO: 현재는 User를 List로 저장하므로 id 사용 X. 나중에 DB 연결시 사용한다.
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;

    public Long getId() {
        return id;
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

    public void setId(Long id) {
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

    public boolean matchPw(String userPw) {
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
