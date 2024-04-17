package codesquad.springcafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String userId;
    private String password;
    private String email;
    private LocalDateTime registerTime;

    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일";

    public User(){
        this.registerTime = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordInputCorrect(String passwordInput){
        return passwordInput.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormattedRegisterTime(){
        return registerTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }

    public LocalDateTime getRegisterTime(){
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime){
        this.registerTime = registerTime;
    }

}
