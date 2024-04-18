package codesquad.springcafe.users.model;

import codesquad.springcafe.users.model.dto.UserUpdateRequest;

import java.security.*;
import java.time.LocalDate;
import java.util.Base64;

public class User {
    private String userId;  // 유저 아이디
    private String email;   // 이메일
    private String name;  // 닉네임
    private String hashedPassword;    // 해싱된 비밀번호
    private LocalDate creationDate; // 생성일 -> 외부에서 생성한 시간을 수정하지못하도록 내부에서 생성

    public User(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.hashedPassword = hashPassword(password);
        this.creationDate = LocalDate.now(); // 현재 날짜를 사용하여 초기화
    }

    private String hashPassword(String password) {
        try {
            // SHA-256 알고리즘을 사용하는 MessageDigest 객체 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 입력 문자열을 바이트 배열로 변환하여 해시 함수에 전달
            byte[] hash = digest.digest(password.getBytes());

            // 해시된 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 16진수 해시 문자열 반환
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 지정된 알고리즘이 없는 경우 예외 처리
            return null;
        }
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void updateUser(UserUpdateRequest updateData) {
        this.name = updateData.getNewName();
        this.email = updateData.getNewEmail();
        this.hashedPassword = hashPassword(updateData.getNewPassword());
    }


}
