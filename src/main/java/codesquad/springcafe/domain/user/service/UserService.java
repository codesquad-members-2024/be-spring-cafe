package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: 예외 클래스 생성해 처리
    public Long join(UserJoinData userJoinData) {
        userRepository.findByEmail(userJoinData.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 사용자입니다.");
                });
        User user = userJoinData.toUser();
        return userRepository.save(user);
    }

    public UserListData getUsers() {
        List<UserData> users = userRepository.findAll().stream()
                .map(u -> new UserData(u.getId(), u.getEmail(), u.getName(),
                        convertCreatedAt(u.getCreatedAt())))
                .toList();

        return new UserListData(users);
    }

    public UserData getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        return new UserData(user.getId(), user.getEmail(), user.getName(), convertCreatedAt(user.getCreatedAt()));
    }

    private String convertCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
