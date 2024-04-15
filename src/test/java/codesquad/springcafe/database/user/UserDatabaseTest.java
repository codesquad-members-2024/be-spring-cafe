package codesquad.springcafe.database.user;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.form.user.UserEditForm;
import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class UserDatabaseTest {
    @Autowired
    UserDatabase userDatabase;

    @BeforeEach
    void setUp() {
        userDatabase.clear();
    }

    @Test
    @DisplayName("데이터베이스에 유저를 저장하면 저장한 유저를 반환한다.")
    void save() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        User saved = userDatabase.add(user);
        assertThat(user).isEqualTo(saved);
    }

    @Test
    @DisplayName("닉네임과 일치하는 유저를 찾을 수 있다.")
    void findByNickname() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        userDatabase.add(user);
        User find = userDatabase.findByNickname("상추").get();
        assertThat(find).isEqualTo(user);
    }

    @Test
    @DisplayName("닉네임과 일치하는 유저를 찾을 수 없으면 빈값을 반환한다.")
    void findByNicknameFailed() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        userDatabase.add(user);
        Optional<User> optionalUser = userDatabase.findByNickname("배추");
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에 저장된 모든 유저를 찾을 수 있다.")
    void findAll() {
        User user1 = new User("sangchu@gmail.com", "상추", "123");
        User user2 = new User("baechu@gmail.com", "배추", "123");

        userDatabase.add(user1);
        userDatabase.add(user2);
        List<User> users = userDatabase.findAll();
        assertThat(users).contains(user1, user2);
    }

    @Test
    void update() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        Long id = userDatabase.add(user).getId();

        UserEditForm form = new UserEditForm("sangchu@gmail.com", "배추", "123", "1234");
        user.update(form.getNickname(), form.getNewPassword());

        userDatabase.update(user);

        User result = userDatabase.findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .get();

        assertThat(result.getNickname()).isEqualTo("배추");
        assertThat(result.getPassword()).isEqualTo("1234");
    }
}