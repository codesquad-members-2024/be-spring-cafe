package codesquad.springcafe.domain.user.data;

/**
 * 각 유저 별 조회 정보를 저장합니다
 */
public class UserData {
    private final Long id;
    private final String email;
    private final String name;
    private final String createdAt;

    public UserData(Long id, String email, String name, String createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
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
