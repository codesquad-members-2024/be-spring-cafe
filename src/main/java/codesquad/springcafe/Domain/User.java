package codesquad.springcafe.Domain;

import java.time.LocalDate;

public class User {
    private String email;
    private String userId;
    private String password;
    private LocalDate signUpDate;

    public User() {
        LocalDate now = LocalDate.now();
        this.signUpDate = now;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    public void infoUpdate(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

