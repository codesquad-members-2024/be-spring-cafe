package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Map<String, User> users = new HashMap<>();

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

    public UserDto findUser(String userId) {
        User found = users.get(userId);
        return createUserDto(found);
    }

    private UserDto createUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private UserDto createUserDto(final long index, User user) {
        UserDto userDto = new UserDto();
        userDto.setIndex(index);
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
