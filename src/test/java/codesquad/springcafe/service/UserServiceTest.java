package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(new UserRepository());
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
}