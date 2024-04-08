package codesquad.springcafe.Repository;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.Domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryImplTest {

    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void beforeEach() {
        userRepository = new UserRepositoryImpl();

        user = new User();
        user.setUserId("eddy");
        user.setEmail("tmdgus717@naver.com");
        user.setPassword("1234");
    }

    @Test
    @DisplayName("UserRepositoryImpl 저장 테스트")
    void save() {
        //when
        userRepository.save(user);
        //then
        //success
        boolean isEddy = userRepository.findById("eddy").isPresent();
        assertThat(isEddy).isTrue();
        //fail
        boolean isSean = userRepository.findById("sean").isPresent();
        assertThat(isSean).isFalse();
    }

    @Test
    @DisplayName("findById를 통해 user 객체 가져오는지 테스트")
    void findById() {
        userRepository.save(user);
        //then
        User storedUser = userRepository.findById(user.getUserId()).get();
        assertThat(storedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("findByEmail를 통해 user 객체 가져오는지 테스트")
    void findByEmail() {
        userRepository.save(user);
        //then
        User storedUser = userRepository.findByEmail(user.getEmail()).get();
        assertThat(storedUser).isEqualTo(user);
    }

    @Test
    void findAll() {
        userRepository.save(user);
        User nextUser = new User();
        nextUser.setUserId("sean");
        nextUser.setEmail("tmdgus717@gmail.com");
        nextUser.setPassword("qwer");
        userRepository.save(nextUser);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUserId()).isEqualTo("eddy");
        assertThat(users.get(1).getUserId()).isEqualTo("sean");
    }
}