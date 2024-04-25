package codesquad.springcafe.model.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String userId;
    private String nickname;
    private String password;
    private String email;
    private LocalDateTime registerTime;

    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일";

    public User(){
        this.registerTime = LocalDateTime.now();
    }

    public User(String userId, String nickname, String password, String email, LocalDateTime registerTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.registerTime = registerTime;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User(String userId, String nickname, String password, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
