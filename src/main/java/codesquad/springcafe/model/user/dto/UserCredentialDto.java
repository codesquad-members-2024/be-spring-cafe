package codesquad.springcafe.model.user.dto;

public class UserCredentialDto {
    private final String password;

    public UserCredentialDto(String password) {
        this.password = password;
    }

    public boolean validatePassword(String inputPassword) {
        return inputPassword.equals(password);
    }
}
