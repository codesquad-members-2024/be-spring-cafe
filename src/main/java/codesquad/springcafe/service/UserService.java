package codesquad.springcafe.service;

import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.model.UpdateUser;
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

    public void update(UpdateUser updateUser) {
        String userId = updateUser.getUserId();
        User user = userRepository.findUserById(userId);
        user.update(updateUser);
        userRepository.update(user);
    }

    public boolean isValidUser(String userId, String password) {
        User user = userRepository.findUserById(userId);
        return user.validatePassword(password);
    }

}
