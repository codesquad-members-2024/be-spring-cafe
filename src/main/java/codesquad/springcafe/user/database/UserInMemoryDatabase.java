package codesquad.springcafe.user.database;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserRequestDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class UserInMemoryDatabase implements UserDatabase {

    private final List<User> userDatabase = new ArrayList<>();


    @Override
    public void save(User user) {
        userDatabase.add(user);
    }

    @Override
    public void update(User user, String userId) {
        User foundUser = findByUserId(userId);
        foundUser.update(user);
    }

    @Override
    public List<UserRequestDto> findAll() {
        List<UserRequestDto> userRequestDtos = new ArrayList<>();
        for (User user : userDatabase) {
            userRequestDtos.add(
                new UserRequestDto(user.getUserId(), user.getEmail(), user.getNickname()));
        }
        return userRequestDtos;
    }

    @Override
    public User findByUserId(String userId) {
        return userDatabase.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 userId가 없습니다"));
    }

    @Override
    public void clear() {
        userDatabase.clear();
    }


}
