package springcafe.user.login.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordHashing {

    public String hashPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("필수 알고리즘이 시스템에 없습니다.", e);
        }
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return byteToHex(encodedhash);
    }

    private String byteToHex(byte[] hash){
        int hashLength = hash.length;
        StringBuilder hexString = new StringBuilder(2 * hashLength);

        for(int i =0; i<hashLength; i++){
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
