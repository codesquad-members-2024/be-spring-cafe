package codesquad.springcafe.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDatabaseTest {

    @Autowired
    UserDatabase userDatabase;

    @AfterEach
    void resetDB() {
        userDatabase.clear();
    }

    @Test
    void testSave() {
        User user = new UserBuilder()
            .userId("userId")
            .email("email")
            .nickname("nickname")
            .password("password")
            .build();

        userDatabase.save(user);
        assertThat(userDatabase.findAll()).hasSize(1);
    }

    @Test
    void testFindAll() {
        User user1 = new UserBuilder()
            .userId("userId1")
            .email("email1")
            .nickname("nickname1")
            .password("password1")
            .build();
        User user2 = new UserBuilder()
            .userId("userId2")
            .email("email2")
            .nickname("nickname2")
            .password("password2")
            .build();

        userDatabase.save(user1);
        userDatabase.save(user2);
        assertThat(userDatabase.findAll()).hasSize(2);
    }
}