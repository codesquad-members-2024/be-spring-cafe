package codesquad.springcafe.service;

import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.model.UpdateUser;
import codesquad.springcafe.model.User;
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

    public void join(User user) {
        userRepository.saveUser(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findUserById(String userId) {
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException(userId);
    }

    public void update(UpdateUser updateUser) {
        String userId = updateUser.getUserId();
        User user = findUserById(userId);
        user.update(updateUser);
        userRepository.update(user);
    }

    public boolean isValidUser(String userId, String password) {
        User user = findUserById(userId);
        return user.validatePassword(password);
    }

}
