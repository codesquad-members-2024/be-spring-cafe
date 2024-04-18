package codesquad.springcafe.users.model;

import codesquad.springcafe.users.model.dto.UserUpdateRequest;

import java.security.*;
import java.time.LocalDate;
import java.util.Base64;

public class User {
    private String userId;  // 유저 아이디
    private String email;   // 이메일
    private String name;  // 닉네임
    private String salt;  // 솔트
    private String hashedPassword;    // 해싱된 비밀번호
    private LocalDate creationDate; // 생성일 -> 외부에서 생성한 시간을 수정하지못하도록 내부에서 생성

    public User(String userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        byte[] saltBytes = generateSalt();
        this.salt = Base64.getEncoder().encodeToString(saltBytes);
        this.hashedPassword = hashPassword(password, saltBytes);
        this.creationDate = LocalDate.now(); // 현재 날짜를 사용하여 초기화
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(salt);
            byte[] hashedBytes = messageDigest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public void setCreationDate(LocalDate creationDate){
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

    public String getSalt() {
        return salt;
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
        byte[] saltBytes = generateSalt();
        this.salt = Base64.getEncoder().encodeToString(saltBytes);
        this.hashedPassword = hashPassword(updateData.getNewPassword(), saltBytes);
    }


}
