package codesquad.springcafe.service.impl;

import codesquad.springcafe.exception.db.UserNotFoundException;
import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.exception.service.UserNotJoinedException;
import codesquad.springcafe.model.ListUser;
import codesquad.springcafe.model.LoginUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.user.UserRepository;
import codesquad.springcafe.service.UserService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserManagementService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) throws DuplicateUserIdException {
        try {
            String userId = user.getUserId();
            validateDuplicateUserId(userId); // 중복 검증
            userRepository.addUser(user);
            logger.info("[사용자 생성 완료] - {}", user);
        } catch (DuplicateUserIdException e) {
            logger.error("이미 중복된 ID를 가진 사용자가 존재합니다.");
            throw new DuplicateUserIdException(user.getUserId());
        }
    }

    private void validateDuplicateUserId(String userId) throws DuplicateUserIdException {
        try {
            Optional<User> optUser = userRepository.findUserByUserId(userId);
            if (optUser.isPresent()) {
                throw new DuplicateUserIdException(optUser.get().getUserId());
            }
        } catch (UserNotFoundException e) {
            logger.info("사용자 생성 가능");
        }
    }

    @Override
    public User findUserByUserId(String userId) {
        Optional<User> optUser = userRepository.findUserByUserId(userId);
        return optUser.orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void updateUser(String userId, UpdatedUser updatedUser) {
        userRepository.updateUser(userId, updatedUser);
        logger.info("[{} 사용자 수정 성공]", userId);
    }

    @Override
    public void deleteUser(String userId) {
        String deletedUserId = userRepository.deleteUser(userId);
        logger.info("[{} 사용자 삭제 성공]", deletedUserId);
    }

    @Override
    public List<ListUser> findAllUser() {
        return userRepository.findAllUser();
    }

    @Override
    public boolean isJoinedUser(LoginUser loginUser) throws UserNotJoinedException {
        String userId = loginUser.getUserId();
        Optional<User> optUser = userRepository.findUserByUserId(userId);
        User user = optUser.orElseThrow(() -> new UserNotJoinedException(userId));
        return user.matchUser(loginUser);
    }

    @Override
    public SessionUser findSessionUserById(String userId) {
        Optional<SessionUser> optSessionUser = userRepository.findSessionUserByUserId(userId);
        return optSessionUser.orElseThrow(() -> new UserNotFoundException(userId));
    }
}
