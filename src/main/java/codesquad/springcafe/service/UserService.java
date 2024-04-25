package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.error.exception.UserNotFoundException;
import codesquad.springcafe.repository.user.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.createUser(user);
        logger.debug("ID {} 사용자 생성", user.getUserId());
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId + "의 사용자가 존재하지 않습니다."));

        logger.debug("ID {} 사용자 조회", userId);
        return user;
    }

    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        userRepository.updateUser(userId, userUpdateDto);
        logger.debug("ID {} 사용자 정보 수정", userId);
    }
}