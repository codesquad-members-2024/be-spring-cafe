package codesquad.springcafe.users.model.dto;

import java.util.Objects;

public class UserCredentialDto {
    private final String password;

    public UserCredentialDto(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserCredentialDto that = (UserCredentialDto) obj;
        return Objects.equals(password, that.password);
    }
}
