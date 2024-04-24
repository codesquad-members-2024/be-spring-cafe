package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;

public class UserCredentialDto {
    private final String userId;
    private final String password;

    public UserCredentialDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public User toEntity(){
        return new User(userId, password);
    }

    public boolean isPasswordMatches(String passwordInput){
        return password.equals(passwordInput);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || this.getClass() != this.getClass()){
            return false;
        }
        UserCredentialDto that = (UserCredentialDto) obj;
        return this.userId.equals(that.userId) && this.password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return userId.hashCode() + password.hashCode();
    }
}
