package codesquad.springcafe.users.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserPreviewDto {
    private final String userId;
    private final String name;
    private final String email;
    private final LocalDateTime creationDate;

    public UserPreviewDto(String userId, String name, String email, LocalDateTime creationDate) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.creationDate.format(formatter);
    }

}
