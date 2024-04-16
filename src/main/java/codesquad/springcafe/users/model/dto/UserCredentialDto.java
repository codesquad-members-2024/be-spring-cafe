package codesquad.springcafe.users.model.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class UserCredentialDto {
    private final String salt;
    private final String hashedPassword;
    public UserCredentialDto(String salt, String hashedPassword) {
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public boolean verifyPassword(String inputPassword) {
        String hashedInputPassword = hashPassword(inputPassword, Base64.getDecoder().decode(salt));
        return hashedInputPassword.equals(hashedPassword);
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


}
