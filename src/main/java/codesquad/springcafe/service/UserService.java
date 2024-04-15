package codesquad.springcafe.service;

import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.UserRepository;
import codesquad.springcafe.web.dto.UserCreateDto;
import codesquad.springcafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserCreateDto userCreateDto) {
        userRepository.save(
                new User(userCreateDto.getUserId(),
                userCreateDto.getPassword(),
                userCreateDto.getName(),
                userCreateDto.getEmail()));
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        User findUser = findOne(userId);
        findUser.setPassword(userUpdateDto.getNewPassword());
        findUser.setName(userUpdateDto.getName());
        findUser.setEmail(userUpdateDto.getEmail());
        userRepository.update(findUser);
    }
}
