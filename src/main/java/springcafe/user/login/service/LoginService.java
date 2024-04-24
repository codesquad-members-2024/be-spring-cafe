package springcafe.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcafe.user.exception.WrongIdPasswordException;
import springcafe.user.login.security.PasswordHashing;
import springcafe.user.model.User;
import springcafe.user.repository.UserDao;

@Service
public class LoginService {

    private UserDao userDao;
    private final PasswordHashing passwordHashing;

    @Autowired
    public LoginService(UserDao userDao, PasswordHashing passwordHashing) {
        this.userDao = userDao;
        this.passwordHashing = passwordHashing;
    }

    public User loginCheck(String id, String password) {
        User user = userDao.findById(id);
        String hashedInputPassword = passwordHashing.hashPassword(password);

        if (!user.matchPassword(hashedInputPassword)) {
            throw new WrongIdPasswordException();
        }

        return user;
    }
}
