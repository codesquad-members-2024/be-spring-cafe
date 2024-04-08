package model;

import java.time.LocalDate;

public class User {
    private String email;   // 이메일
    private String userId;  // 유저 아이디 = 닉네임
    private String password;    // 비밀번호
    private LocalDate creationDate; // 생성일 -> 외부에서 생성한 시간을 수정하지못하도록 내부에서 생성

    public User(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.creationDate = LocalDate.now(); // 현재 날짜를 사용하여 초기화
    }

    @Override
    public String toString() {
        return "email : " + email + ", userId : " + userId + ", password : " + password + ", creationDate : " + creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
