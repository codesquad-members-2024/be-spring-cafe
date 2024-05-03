package codesquad.springcafe.domain.user.dto;

public class UserIdentity {

    private String userId;
    private String name;

    public UserIdentity(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
