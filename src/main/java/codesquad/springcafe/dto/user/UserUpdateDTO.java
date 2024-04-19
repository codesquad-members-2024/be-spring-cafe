package codesquad.springcafe.dto.user;

import codesquad.springcafe.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUpdateDTO {
    private final String password;
    private final String name;
    private final String email;

    public UserUpdateDTO(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toUser(String userId, PasswordEncoder passwordEncoder) {
        String hashedPassword = passwordEncoder.encode(password);
        return new User(userId, hashedPassword, name, email);
    }
}