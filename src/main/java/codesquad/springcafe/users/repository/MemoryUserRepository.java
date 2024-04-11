package codesquad.springcafe.users.repository;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import db.UserDatabase;
import model.User;
import model.UserData;
import model.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);

    @Override
    public void createUser(UserData userData) {
        String userId = userData.getUserId();
        String email = userData.getEmail();
        String name = userData.getName();
        String password = userData.getPassword();

        User user = new User(userId, email, name, password);
        logger.debug("User Created : {}", user);

        UserDatabase.addUser(user);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return UserDatabase.getAllUsers();
    }

    @Override
    public User findUserById(String userId) {
        return UserDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        User user = findUserById(userId);
        validatePassword(user.getPassword(), updateData.getCurrentPassword());
        user.updateUser(updateData);
        logger.debug("User Updated : {}", user);
    }

    private void validatePassword(String userPassword, String inputPassword) {
        if (!userPassword.equals(inputPassword)) {
            throw new PasswordMismatchException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }

}
