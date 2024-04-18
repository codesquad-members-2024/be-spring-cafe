package codesquad.springcafe.utils;


import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SHA256HashService {

    public String hashPassword(String password) {
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
}