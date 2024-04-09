package codesquad.springcafe.database.user;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserMemoryDatabaseTest {

    UserMemoryDatabase userMemoryDatabase = new UserMemoryDatabase();

    @Test
    @DisplayName("데이터베이스에 유저를 저장하면 저장한 유저를 반환한다.")
    void save() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        User saved = userMemoryDatabase.save(user);
        assertThat(user).isEqualTo(saved);
    }

    @Test
    @DisplayName("닉네임과 일치하는 유저를 찾을 수 있다.")
    void findByNickname() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        userMemoryDatabase.save(user);
        User find = userMemoryDatabase.findBy("상추").get();
        assertThat(find).isEqualTo(user);
    }

    @Test
    @DisplayName("닉네임과 일치하는 유저를 찾을 수 없으면 빈값을 반환한다.")
    void findByNicknameFailed() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        userMemoryDatabase.save(user);
        Optional<User> optionalUser = userMemoryDatabase.findBy("배추");
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에 저장된 모든 유저를 찾을 수 있다.")
    void findAll() {
        User user1 = new User("sangchu@gmail.com", "상추", "123");
        User user2 = new User("baechu@gmail.com", "배추", "123");

        userMemoryDatabase.save(user1);
        userMemoryDatabase.save(user2);
        List<User> users = userMemoryDatabase.findAll();
        assertThat(users).contains(user1, user2);
    }
}