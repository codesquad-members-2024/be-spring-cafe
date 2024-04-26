package codesquad.springcafe.service;

import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.UserRepository;
import codesquad.springcafe.web.dto.LoginDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidUser(LoginDto loginDto) {
        Optional<User> findUser = userRepository.findByUserId(loginDto.getUserId());
        return findUser.isPresent() && loginDto.getPassword().equals(findUser.get().getPassword());
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId).get();
    }
}
