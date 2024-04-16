package codesquad.springcafe.users.model.dto;

public class UserPreviewDto {
    private final String userId;
    private final String name;
    private final String email;
    private final String creationDate;

    public UserPreviewDto(String userId, String name, String email, String creationDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.creationDate = creationDate;
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

    public String getCreationDate() {
        return creationDate;
    }
}
