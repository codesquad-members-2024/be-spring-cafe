package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.createUser(user);
    }
}
