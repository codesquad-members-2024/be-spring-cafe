package codesquad.springcafe.global.security;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * 비밀번호를 암호화하고, 저장된 비밀번호와 입력한 비밀번호가 같은지 확인하는 클래스
 */
public class PasswordEncoder {

    @Value("${pwd.encrypt.secret-key}")
    private String SECRET_KEY;

    private static final int SALT_LENGTH = 20;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATION = 65536;
    private static final int KEY_LENGTH = 256;

    /**
     * 비밀번호를 암호화하는 메서드
     */
    public String encode(CharSequence rawPassword) {
        byte[] salt = generateSalt();
        byte[] encoded = encode(rawPassword, salt);
        return Base64.getEncoder().encodeToString(encoded);
    }

    /**
     * 입력된 비밀번호를 암호화한 결과가 저장된 암호화된 비밀번호와 같은지 확인하는 메서드
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = Base64.getDecoder().decode(encodedPassword);
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(digested, 0, salt, 0, SALT_LENGTH);
        return MessageDigest.isEqual(digested, encode(rawPassword, salt));
    }

    /**
     * salt로 평문 비밀번호를 암호화하는 메서드
     */
    private byte[] encode(CharSequence rawPassword, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(),
                    concatenate(salt, SECRET_KEY.getBytes(StandardCharsets.UTF_8)),
                    ITERATION, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return concatenate(salt, hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("해싱이 불가능합니다.", e);
        }
    }

    /**
     * 여러 개의 바이트 배열을 하나로 합치는 메서드
     */
    private byte[] concatenate(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] newArray = new byte[length];
        int destPos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, newArray, destPos, array.length);
            destPos += array.length;
        }
        return newArray;
    }

    /**
     * salt를 생성하는 메서드
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        return salt;
    }
}
