package codesquad.springcafe.service;

import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.UserMemoryRepository;
import codesquad.springcafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    UserRepository userRepository = new UserMemoryRepository();
    UserService userService = new UserService(userRepository);

    @BeforeEach
    void init() {
        userRepository.clear();
    }

    @Test
    @DisplayName("회원가입 기능")
    void register() {
        User user1 = new User("test1", "1234", "test1", "test1@naver.com");
        User user2 = new User("test2", "1234", "test2", "test2@naver.com");
        userService.join(user1);
        userService.join(user2);

        List<User> allUsers = userService.findAllUsers();

        assertThat(allUsers).containsOnly(user1, user2);
    }

    @Test
    @DisplayName("유저 시퀀스로 유저 정보를 조회할 수 있다.")
    void findUser() {
        User user = new User("test1", "1234", "test1", "test1@naver.com");
        userService.join(user);

        User findUser = userService.findUserById(user.getUserId());

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("유저 정보를 수정할 수 있다.")
    void updateUserTest() {
        User user = new User("cori", "1234", "old name", "cori@naver.com");
        userRepository.saveUser(user);

        UserUpdateDto userUpdateDto = new UserUpdateDto("cori", "1234", "4321", "new name", "cori123@naver.com");
        userService.update(userUpdateDto);

        assertThat(user).usingRecursiveComparison().isEqualTo(new User("cori", "4321", "new name", "cori123@naver.com"));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    void passwordNotMatchTest() {
        User user = new User("cori", "1234", "old name", "cori@naver.com");
        userRepository.saveUser(user);

        UserUpdateDto userUpdateDto = new UserUpdateDto("cori", "1111", "4321", "new name", "cori123@naver.com");

        assertThatThrownBy(() -> userService.update(userUpdateDto)).isInstanceOf(IllegalArgumentException.class);
    }
}