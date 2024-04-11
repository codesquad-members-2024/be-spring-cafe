package codesquad.springcafe.user;

public class UserDto {
    private Long index;
    private String userId;
    private String password;
    private String name;
    private String email;

    public Long getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
