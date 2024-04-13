package codesquad.springcafe.user;

public class UserDto {
    private long index;
    private String userId;
    private String name;
    private String email;

    public void setIndex(long index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
