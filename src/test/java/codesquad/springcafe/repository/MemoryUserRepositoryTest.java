package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        User user = new User();
        user.setId("ddd");
        user.setName("abc");
        user.setPassword("ddd");
        user.setEmail("ddd@naver.com");

        repository.save(user);
        User result = repository.findById("ddd").get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findById() {
        User user1 = new User();
        user1.setId("ddd");

        User user2 = new User();
        user2.setId("abc");

        repository.save(user1);
        repository.save(user2);

        User result = repository.findById("ddd").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    void findAll() {
        User userA = new User();
        User userB = new User();

        userA.setId("asdf");
        userB.setId("ddd");

        repository.save(userA);
        repository.save(userB);

        List<User> users = repository.findAll();

        assertThat(users).contains(userA, userB);

    }
}