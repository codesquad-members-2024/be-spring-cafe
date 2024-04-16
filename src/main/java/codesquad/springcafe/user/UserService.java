package codesquad.springcafe.user;

import codesquad.springcafe.user.database.UserDatabase;
import codesquad.springcafe.user.dto.UserRequestDto;
import codesquad.springcafe.user.dto.UserSigninDto;
import codesquad.springcafe.user.dto.UserSignupDto;
import codesquad.springcafe.user.dto.UserUpdateDto;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void save(UserSignupDto userSignupDto) {
        userDatabase.save(userSignupDto.toEntity());
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

    public void updateUserProfile(UserUpdateDto userUpdateDto, String userId) {
        User foundUser = findByUserId(userId);
        if (foundUser.checkPassword(userUpdateDto.getPassword())) {
            userDatabase.update(userUpdateDto.toEntity(), userId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public User userLogin(UserSigninDto userSigninDto) {
        User foundUser = findByUserId(userSigninDto.getUserId());

        if (foundUser != null && foundUser.checkPassword(userSigninDto.getPassword())) {
            return foundUser;
        }
        return null;
    }

}
