package codesquad.springcafe.user;

import codesquad.springcafe.user.dto.UserCreationDto;
import codesquad.springcafe.user.dto.UserViewDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

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

    public void updateUser(String userId, UserCreationDto dto) {
        User user = dto.toEntity(userId);
        dao.update(user);
    }

    public Optional<UserViewDto> doLogin(String userId, String password) {
        return dao.findUser(userId)
                .filter(user -> user.has(password))
                .map(UserViewDto::toDto);
    }
}
