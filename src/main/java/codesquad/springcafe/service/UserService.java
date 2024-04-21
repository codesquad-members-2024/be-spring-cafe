package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.MemoryUserRepository;
import codesquad.springcafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입
     */
    public String join(User user) {
        validateDuplicateUser(user); // 중복 회원 검증
        userRepository.addUser(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        // 같은 이름이 있는 중복 회원 X
        userRepository.findUserById(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findUserById(userId);
    }

    public void userUpdate(String userId, User updatedUser) {
        Optional<User> optionalUser = findOne(userId);
        optionalUser.ifPresent(user -> {
                    user.setName(updatedUser.getName());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                }
        );
        userRepository.userUpdate(updatedUser);
    }
}
