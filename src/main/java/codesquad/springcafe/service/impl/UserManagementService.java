package codesquad.springcafe.service.impl;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import codesquad.springcafe.repository.user.UserRepository;
import codesquad.springcafe.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public String updateUser(String userId, UpdatedUser updatedUser) throws Exception {
        return userRepository.updateUser(userId, updatedUser);
    }

    @Override
    public String deleteUser(String userId) {
        return userRepository.deleteUser(userId);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllUser();
    }
}
