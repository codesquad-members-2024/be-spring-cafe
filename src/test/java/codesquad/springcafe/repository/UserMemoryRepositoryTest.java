package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMemoryRepositoryTest {

    UserRepository userRepository = new UserMemoryRepository();

    @BeforeEach
    void init() {
        userRepository.clear();
    }

    @Test
    @DisplayName("유저 정보를 저장할 수 있다.")
    void saveUser() {
        User user = new User("test", "1234", "test", "test@naver.com");

        userRepository.saveUser(user);

        assertThat(userRepository.findUserById(user.getUserId())).isEqualTo(user);
    }

    @Test
    @DisplayName("저장한 유저를 전부 가져올 수 있다.")
    void findAllUsers() {
        User user1 = new User("test1", "1234", "test1", "test1@naver.com");
        User user2 = new User("test2", "1234", "test2", "test2@naver.com");

        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        assertThat(userRepository.findAllUsers()).containsOnly(user1, user2);
    }

}