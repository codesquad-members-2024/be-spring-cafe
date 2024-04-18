package codesquad.springcafe.dto.user;

public class UserInfoDTO {
    private final Long index;
    private final String userId;
    private final String name;
    private final String email;

    public UserInfoDTO(Long index, String userId, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
