package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserLoginDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.error.exception.AccessDeniedException;
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
                .orElseThrow(() -> new UserNotFoundException(userId + " 사용자가 존재하지 않습니다."));

        logger.debug("ID {} 사용자 조회", userId);
        return user;
    }

    public User loginUser(UserLoginDto userLoginDto) {
        User user = findByUserId(userLoginDto.getUserId());

        if (!user.matchPassword(userLoginDto.getPassword())) {
            throw new AccessDeniedException(user.getUserId() + " 사용자의 비밀번호가 틀렸습니다.");
        }

        logger.debug("ID {} 사용자 로그인", user.getUserId());
        return user;
    }

    public void updateUser(User loginUser, UserUpdateDto userUpdateDto) {
        if (loginUser == null) {
            throw new UserNotFoundException("로그인 사용자가 존재하지 않습니다.");
        }

        if (!loginUser.matchPassword(userUpdateDto.getOldPassword())) {
            throw new AccessDeniedException(loginUser.getUserId() + " 사용자의 비밀번호가 틀렸습니다.");
        }

        userRepository.updateUser(loginUser.getUserId(), userUpdateDto);
        logger.debug("ID {} 사용자 정보 수정", loginUser.getUserId());
    }
}