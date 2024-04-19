package codesquad.springcafe.model;

public class ListUser {
    private final long id;
    private final String userId;
    private final String userName;
    private final String userEmail;

    public ListUser(long id, String userId, String userName, String userEmail) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public long getId() {
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

}