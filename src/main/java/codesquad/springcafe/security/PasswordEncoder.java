package codesquad.springcafe.security;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordEncoder {
    private static final String SECRET_KEY = "mySecretKey123!";
    private static final int SALT_LENGTH = 10;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATION = 100;
    private static final int KEY_LENGTH = 256;


    public String encode(String rawPassword) {
        byte[] salt = generateSalt();
        byte[] encoded = encode(rawPassword, salt);
        return Base64.getEncoder().encodeToString(encoded);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        byte[] digested = Base64.getDecoder().decode(encodedPassword);
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(digested, 0, salt, 0, SALT_LENGTH);
        return MessageDigest.isEqual(digested, encode(rawPassword, salt));
    }

    private byte[] encode(String rawPassword, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(
                    rawPassword.toCharArray(),
                    concatenate(salt, SECRET_KEY.getBytes(StandardCharsets.UTF_8)),
                    ITERATION,
                    KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return concatenate(salt, hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException();
        }
    }

    private byte[] concatenate(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays){
            length += array.length;
        }
        byte[] newArray = new byte[length];
        int destPos = 0;
        for (byte[] array : arrays){
            System.arraycopy(array, 0, newArray, destPos, array.length);
            destPos += array.length;
        }
        return newArray;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

}
