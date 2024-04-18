package codesquad.springcafe.Domain;

import java.time.LocalDate;

public class UserCredential {
    private String email;
    private String userId;
    private LocalDate signUpDate;

    public UserCredential(String email, String userId, LocalDate signUpDate) {
        this.email = email;
        this.userId = userId;
        this.signUpDate = signUpDate;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getSignUpDate() {
        return signUpDate;
    }
}
