package codesquad.springcafe.web.service;

import codesquad.springcafe.web.repository.UserRepository;
import codesquad.springcafe.web.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {

        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUser(String userName) {
        return userRepository.findByName(userName);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                        .ifPresent(u -> {
                            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
                        });
    }

}
