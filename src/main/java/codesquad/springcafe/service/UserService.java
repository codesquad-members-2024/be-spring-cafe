package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.getAll();
    }

    public User findById(String userId) {
        Optional<User> targetUser = userRepository.getById(userId);
        return targetUser.orElse(null);
    }

    public void updateInfo(User modifiedUser) {
        userRepository.modify(modifiedUser);
    }

    public Optional<User> authenticate(String userId, String password) {
        Optional<User> targetUser = userRepository.getById(userId);
        if (targetUser.isPresent() && targetUser.get().checkPassword(password)) {
            return targetUser;
        }
        return Optional.empty();
    }
}