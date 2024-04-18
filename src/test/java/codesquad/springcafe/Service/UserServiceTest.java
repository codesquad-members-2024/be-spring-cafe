package codesquad.springcafe.Service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Repository.UserRepository;
import codesquad.springcafe.Repository.UserRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    void signUp() {
        User user = new User();
        user.setUserId("eddy");
        user.setEmail("tmdgus717@naver.com");
        user.setPassword("1234");
        //when
        when(userRepository.create(any(User.class))).thenReturn(user);

        UserService userService = new UserService(userRepository);
        userService.signUp(user);
        //
        assertThat(user.getUserId()).isEqualTo("eddy");
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
        UserService userService = new UserService(userRepository);
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
        UserService userService = new UserService(userRepository);
        userService.signUp(user1);
        userService.signUp(user2);

        List<User> users = userService.findAllUser();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user1);
    }
}