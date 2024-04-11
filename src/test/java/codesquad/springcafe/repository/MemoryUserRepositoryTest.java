package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import org.apache.catalina.mbeans.UserMBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    @Test
    void save() {
        User user = new User();
        user.setId("ddd");
        user.setName("abc");
        user.setPassword("ddd");
        user.setEmail("ddd@naver.com");

        User savedUser = repository.save(user);
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void findById() {
        User user = new User();
        user.setId("ddd");
        user.setName("abc");
        user.setPassword("ddd");
        user.setEmail("ddd@naver.com");

        User savedUser = repository.save(user);
        User findUser = repository.findById("ddd").get();
        assertThat(savedUser).isEqualTo(findUser);
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