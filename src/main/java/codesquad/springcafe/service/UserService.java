package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.repository.UserRepository;
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
        return userRepository.findByUserId(userId).get();
    }

    public Map<Long, User> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        User findUser = findOne(userId);
        Long findSequence = userRepository.findSequence(findUser);
        findUser.setPassword(userUpdateDto.getPassword());
        findUser.setName(userUpdateDto.getName());
        findUser.setEmail(userUpdateDto.getEmail());
        userRepository.update(findSequence, findUser);
    }
}
