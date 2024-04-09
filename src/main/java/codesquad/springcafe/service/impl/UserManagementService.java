package codesquad.springcafe.service.impl;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.repository.UserRepository;
import codesquad.springcafe.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public User findUserByUserId(String userId) throws Exception {
        return userRepository.findUserByUserID(userId);
    }

    @Override
    public User modifyUser(User user) throws Exception {
        return userRepository.modifyUser(user);
    }

    @Override
    public User deleteUser(User user) {
        return userRepository.deleteUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllUser();
    }
}
