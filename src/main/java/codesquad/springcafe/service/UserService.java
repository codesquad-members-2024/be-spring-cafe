package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UpdateUser;
import codesquad.springcafe.domain.repository.UserRepository;
import codesquad.springcafe.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewUser(User user) {
        userRepository.add(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUserById(String userId) {
        return userRepository.getById(userId).orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    public boolean editUserProfile(UpdateUser updateUser, User target) {
        String password = updateUser.getPassword();

        if (target.checkPassword(password)) {
            target.setPassword(updateUser.getNewPassword());
            target.setName(updateUser.getName());
            target.setEmail(updateUser.getEmail());
            userRepository.update(target);
            return true;
        }
        return false;
    }
}
