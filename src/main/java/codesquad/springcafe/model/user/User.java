package codesquad.springcafe.model.user;

import codesquad.springcafe.model.user.dto.UserUpdateData;

import java.time.LocalDate;

public class User {
    private String userId;  // 유저 아이디
    private String email;   // 이메일
    private String name;  // 닉네임
    private String password;    // 비밀번호
    private LocalDate creationDate; // 생성일 -> 외부에서 생성한 시간을 수정하지못하도록 내부에서 생성

    public User(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.creationDate = LocalDate.now(); // 현재 날짜를 사용하여 초기화
    }

    public User(String userId, String email, String name, String password, String creationDate) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.creationDate = LocalDate.parse(creationDate);
    }

    @Override
    public String toString() {
        return "ID : " + userId + ", EMAIL : " + email + ", Name : " + name + ", PW : " + password + ", CreationDate : " + creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void updateUser(UserUpdateData updateData) {
        this.name = updateData.getNewName();
        this.email = updateData.getNewEmail();
        this.password = updateData.getNewPassword();
    }

}
