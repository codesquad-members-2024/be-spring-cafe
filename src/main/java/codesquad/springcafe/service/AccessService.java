package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.LoginInformation;
import codesquad.springcafe.exception.LoginFailException;
import codesquad.springcafe.domain.repository.UserRepository;
import codesquad.springcafe.exception.UserNotFoundException;
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
        User target = userRepository.getById(loginInformation.getUserId()).orElseThrow(() -> new UserNotFoundException("해당되는 유저가 없습니다."));

        if (!target.checkPassword(loginInformation.getPassword())) {
            throw new LoginFailException("로그인 실패");
        }
        return target;
    }
}