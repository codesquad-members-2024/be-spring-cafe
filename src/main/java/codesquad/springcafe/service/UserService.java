package codesquad.springcafe.service;

import codesquad.springcafe.repository.UserRepository;
import codesquad.springcafe.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.saveUser(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findUserById(String userId) {
        return userRepository.findUserById(userId);
    }
}
