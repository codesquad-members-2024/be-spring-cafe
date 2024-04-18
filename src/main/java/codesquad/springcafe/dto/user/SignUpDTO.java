package codesquad.springcafe.dto.user;

import codesquad.springcafe.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignUpDTO {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public SignUpDTO(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(password);
        return new User(userId, encodedPassword, name, email);
    }
}