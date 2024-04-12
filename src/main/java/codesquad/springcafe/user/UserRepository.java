package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Map<String, User> users = new LinkedHashMap<>();

    public void save(final UserDto userDto) {
        final String userId = userDto.getUserId();
        User user = new User(userId, userDto.getPassword(), userDto.getName(), userDto.getEmail());
        log.debug("회원가입한 유저 정보 : {}", user);
        users.put(userId, user);
        log.debug("유저 수 : {}", users.size());
    }

    public List<UserDto> getUserList() {
        return users.values().stream()
                .map(user -> {
                    long index = 0L;
                    return createUserDto(++index, user);
                })
                .toList();
    }

    public Optional<User> findUser(String userId) {
        return Optional.ofNullable(users.get(userId));
    }
}
