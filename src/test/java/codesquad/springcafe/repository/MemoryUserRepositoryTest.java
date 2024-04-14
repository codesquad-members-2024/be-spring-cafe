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
        User user = new User("ddd", "abc", "ddd", "ddd@naver.com");

        repository.save(user);
        User result = repository.findById("ddd").get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findById() {
        User user1 = new User("ddd", "abc", "ddd", "ddd@naver.com");

        User user2 = new User("abc", "abc", "abc", "abc@naver.com");

        repository.save(user1);
        repository.save(user2);

        User result = repository.findById("ddd").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    void findAll() {
        User userA = new User("asdf", "asdf", "asdf", "asdf@asdf.com");
        User userB = new User("ddd", "ddd", "ddd", "ddd@ddd.com");


        repository.save(userA);
        repository.save(userB);

        List<User> users = repository.findAll();

        assertThat(users).contains(userA, userB);

    }
}