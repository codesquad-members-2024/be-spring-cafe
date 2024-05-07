package codesquad.springcafe.service;

import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.exception.UserAccessException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.form.user.UserAddForm;
import codesquad.springcafe.form.user.UserEditForm;
import codesquad.springcafe.model.User;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User createUser(UserAddForm userAddForm) {
        User user = new User(userAddForm.getEmail(), userAddForm.getNickname(), userAddForm.getPassword(),
                LocalDate.now());
        userDatabase.add(user);
        logger.info("새로운 유저가 생성되었습니다. {}", user);
        return user;
    }

    public User findUserByNickname(String nickname) {
        return userDatabase.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException(nickname));
    }

    public User updateUser(String accessNickname, User targetUser, UserEditForm userEditForm) {
        validateAccess(targetUser, accessNickname);

        User updateUser = targetUser.update(userEditForm.getNickname(), userEditForm.getNewPassword());
        userDatabase.update(updateUser);
        logger.info("유저정보가 업데이트 되었습니다. {}", updateUser);
        return updateUser;
    }

    public void validateAccess(User targetUser, String accessNickname) {
        if (!accessNickname.equals(targetUser.getNickname())) {
            throw new UserAccessException();
        }
    }


    public boolean isDuplicateNickname(String nickname) {
        return userDatabase.findByNickname(nickname).isPresent();
    }

    public boolean isUpdatableNickname(User user, String newNickname) {
        if (isOwnNickname(user, newNickname)) {
            return true;
        }
        return !isDuplicateNickname(newNickname);
    }

    public boolean isDuplicateEmail(String email) {
        return userDatabase.findByEmail(email).isPresent();
    }

    public boolean isCorrectPassword(User user, String password) {
        return user.hasSamePassword(password);
    }

    public List<User> getAllUsers() {
        return userDatabase.findAll();
    }

    public UserEditForm getUserEditForm(String nickname, String accessNickname) {
        User user = findUserByNickname(nickname);
        validateAccess(user, accessNickname);
        return new UserEditForm(user.getEmail(), user.getNickname(), null, null);
    }

    private boolean isOwnNickname(User user, String nickname) {
        return user.hasSameNickname(nickname);
    }

}
