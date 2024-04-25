package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return null이면 로그인 실패
     */
    public User login(String loginId, String password) {
        return userRepository.findUserById(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
