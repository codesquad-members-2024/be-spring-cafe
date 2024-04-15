package codesquad.springcafe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private final String userId;
    private final String password;
    private final String email;
    private final LocalDateTime registerTime;

    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일";

    public User(String email, String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.registerTime = LocalDateTime.now();
    }
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFormattedRegisterTime(){
        return registerTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }
}
