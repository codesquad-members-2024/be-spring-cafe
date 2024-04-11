package codesquad.springcafe.Service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Repository.UserRepository;
import codesquad.springcafe.Repository.UserRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;


    @BeforeEach
    public void beforeEach() {
        this.userRepository = new UserRepositoryImpl();
        this.userService = new UserService(userRepository);
    }

    @Test
    void signUp() {
        User user = new User();
        user.setUserId("eddy");
        user.setEmail("tmdgus717@naver.com");
        user.setPassword("1234");

        userService.signUp(user);
        assertThat(userRepository.findById("eddy").get()).isEqualTo(user);
    }

    @Test
    void duplicateTest() {
        User user = new User();
        user.setUserId("eddy");
        user.setEmail("tmdgus717@naver.com");
        user.setPassword("1234");

        User user2 = new User();
        user2.setUserId("eddy");
        user2.setEmail("tmdgus717@gmail.com");
        user2.setPassword("password");
        userService.signUp(user);
        //예외발생 테스트코드
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.signUp(user2);
        });
        assertThat(e.getMessage()).isEqualTo("이미 유저가 존재함");
    }
    @Test
    void findAllUser() {
        User user1 = new User();
        user1.setUserId("eddy");
        user1.setEmail("tmdgus717@naver.com");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUserId("sean");
        user2.setEmail("tmdgus717@gmail.com");
        user2.setPassword("password");
        userService.signUp(user1);
        userService.signUp(user2);

        List<User> users = userService.findAllUser();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user1);
    }
}