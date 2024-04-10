package codesquad.springcafe.users.service;

import db.UserDatabase;
import model.User;
import model.UserData;
import model.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
public class UserManagementService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    @Override
    public void createUser(UserData userData) {
        String email = userData.getEmail();
        String userId = userData.getUserId();
        String password = userData.getPassword();

        User user = new User(email, userId, password);
        logger.debug("User Created : {}", user);

        UserDatabase.addUser(user);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return UserDatabase.getAllUsers();
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return UserDatabase.findUserById(userId);
    }

    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        /*
         * 사용자의 입력이 잘못된 경우 : IllegalArgumentException
         */

        User user = findUserById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        validatePassword(user.getPassword(), updateData.getCurrentPassword());
        
    }

    private void validatePassword(String userPassword, String inputPassword) {
        if (!userPassword.equals(inputPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }




}
