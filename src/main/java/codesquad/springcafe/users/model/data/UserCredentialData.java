package codesquad.springcafe.users.model.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserCredentialData {
    private final String hashedPassword;

    public UserCredentialData(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserCredentialData other = (UserCredentialData) obj;
        return this.hashedPassword.equals(other.hashedPassword);
    }

    @Override
    public String toString(){
        return this.hashedPassword;
    }
}
