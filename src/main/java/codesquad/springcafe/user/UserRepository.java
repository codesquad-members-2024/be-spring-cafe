package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Map<Long, User> usersByIndex = new HashMap<>();
    private static long index;

    public void save(final UserDto userDto) {
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        log.debug("회원가입한 유저 정보 : {}", user);
        usersByIndex.put(++index, user);
        log.debug("유저 수 : {}", usersByIndex.size());
    }

    public List<UserDto> getUserList() {
        return usersByIndex.entrySet().stream()
                .map(userByIndex -> {
                    Long index = userByIndex.getKey();
                    User user = userByIndex.getValue();
                    return setUserDto(index, user);
                })
                .toList();
    }

    private UserDto setUserDto(Long index, User user) {
        UserDto userDto = new UserDto();
        userDto.setIndex(index);
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
