package codesquad.springcafe.domain.user.data;

/**
 * 각 유저 별 조회 정보를 저장합니다
 */
public class UserResponse {
    private final String loginId;
    private final String email;
    private final String name;
    private final String createdAt;

    public UserResponse(String loginId, String email, String name, String createdAt) {
        this.loginId = loginId;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
