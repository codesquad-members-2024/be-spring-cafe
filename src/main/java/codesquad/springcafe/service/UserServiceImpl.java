package codesquad.springcafe.service;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signup(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        // 1. 사용자 존재하는지 확인하기
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found : " + userId));

        // 2. 정보 업데이트 하기
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        // 3. 저장하기
        userRepository.save(user);

        return user;
    }
}
