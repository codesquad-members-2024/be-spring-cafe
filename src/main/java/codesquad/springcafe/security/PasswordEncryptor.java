package codesquad.springcafe.security;

import java.security.MessageDigest;

public class PasswordEncryptor {

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean match(String password, String encrypted) {
        return encrypt(password).equals(encrypted);
    }

}
