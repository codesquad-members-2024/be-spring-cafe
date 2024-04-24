package codesquad.springcafe.dto.user;

public class UserInfoDTO {
    private final Long order;
    private final String userId;
    private final String name;
    private final String email;

    public UserInfoDTO(Long order, String userId, String name, String email) {
        this.order = order;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getOrder() {
        return order;
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
