package codesquad.springcafe.service;

import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.model.User;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {
    private final UserDatabase userDatabase;

    public LoginService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public Optional<User> getMatchedUser(String email, String password) {
        return userDatabase.findByEmail(email).stream()
                .filter(user -> user.hasSamePassword(password))
                .findAny();
    }
}
