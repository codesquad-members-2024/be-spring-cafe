package codesquad.springcafe.user;

import codesquad.springcafe.user.dto.UserCreationDto;
import codesquad.springcafe.user.dto.UserInfoEditRequestDto;
import codesquad.springcafe.user.dto.UserViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public UserViewDto profile(String userId) {
        return dao.findUser(userId)
                .map(UserViewDto::toProfileDto)
                .orElseThrow(() -> new IllegalArgumentException(userId + "를 찾을 수 없습니다"));
    }

    public void save(UserCreationDto dto) {
        User user = dto.toEntity();
        dao.save(user);
    }

    public List<UserViewDto> getAllUsers() {
        return dao.getAllUsers().stream()
                .map(UserViewDto::toDto)
                .collect(Collectors.toList());
    }

    public void updateUser(String userId, UserInfoEditRequestDto dto) {
        Optional<User> optUser = dao.findUser(userId);
        log.debug("개인 정보 수정을 요청한 유저의 기존 정보 : {}", optUser);
        final String originPassword = dto.getPassword();
        optUser.filter(user -> user.has(originPassword))
                .ifPresent(user -> {
                    User edited = dto.toEditedEntity(userId);
                    dao.update(edited);
                });
    }

    public boolean canLogin(String userId, String password) {
        return dao.findUser(userId)
                .map(user -> user.has(password))
                .orElseThrow(() -> new IllegalArgumentException(userId + "는 로그인 할 수 없습니다."));
    }
}
