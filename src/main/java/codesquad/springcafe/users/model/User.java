package codesquad.springcafe.users.model;

import codesquad.springcafe.users.model.dto.UserUpdateDto;
import codesquad.springcafe.users.model.dto.UserUpdateRequest;

import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

public class User {
    private String userId;  // 유저 아이디
    private String email;   // 이메일
    private String name;  // 닉네임
    private String hashedPassword;    // 해싱된 비밀번호
    private LocalDateTime creationDate; // 생성일 -> 외부에서 생성한 시간을 수정하지못하도록 내부에서 생성

    public User(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.hashedPassword = password;
        this.creationDate = LocalDateTime.now(); // 현재 날짜를 사용하여 초기화
    }

    @Override
    public String toString() {
        return "ID : " + userId + ", EMAIL : " + email + ", Name : " + name + ", CreationDate : " + creationDate;
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


    public String getHashedPassword() {
        return hashedPassword;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
