package codesquad.springcafe.user;

import codesquad.springcafe.user.database.UserDatabase;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void save(User user) {
        userDatabase.save(user);
    }

    public void update(User user, String userId) {
        userDatabase.update(user, userId);
    }

    public User findByUserId(String userId) {
        return userDatabase.findByUserId(userId);
    }

    public List<UserRequestDto> findAll() {
        return userDatabase.findAll();
    }

    public void clear() {
        userDatabase.clear();
    }

    public void updateUserProfile(UserUpdateDto userUpdateDto, String userId) {
        User foundUser = findByUserId(userId);
        if (foundUser.checkPassword(userUpdateDto.getPassword())) {
            userDatabase.update(userUpdateDto.toEntity(), userId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
