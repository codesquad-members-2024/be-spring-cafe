package codesquad.springcafe.service;

import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.UserRepository;
import codesquad.springcafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Map<String, User> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        User findUser = findOne(userId);
        findUser.setPassword(userUpdateDto.getPassword());
        findUser.setName(userUpdateDto.getName());
        findUser.setEmail(userUpdateDto.getEmail());
        userRepository.update(findUser);
    }
}
