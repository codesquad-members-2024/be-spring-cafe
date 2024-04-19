package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.LoginInformation;
import codesquad.springcafe.exception.LoginFailException;
import codesquad.springcafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {
    private final UserRepository userRepository;

    @Autowired
    public AccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateUser(LoginInformation loginInformation) throws LoginFailException {
        User target = userRepository.getById(loginInformation.getUserId());

        if (!target.checkPassword(loginInformation.getPassword())) {
            throw new LoginFailException("로그인 실패");
        }
        return target;
    }
}