package codesquad.springcafe.user.dto;

public class UserLoginRequestDto {
    private String userId;
    private String password;

    public UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
