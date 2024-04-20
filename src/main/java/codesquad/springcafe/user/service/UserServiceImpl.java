package codesquad.springcafe.user.service;

import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.LoginUser;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.domain.UserIdentity;
import codesquad.springcafe.user.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.storeUser(user);
    }

    @Override
    public List<User> getAllUsersAsList() {
        return userRepository.getAllUsers();
    }

    @Override
    public User findUserByName(String name) throws NoSuchUserException {
        return userRepository.findByName(name);
    }

    @Override
    public User findUserById(String id) throws NoSuchUserException {
        return userRepository.findById(id);
    }

    @Override
    public UserIdentity loginVerification(LoginUser loginUser) throws CanNotLoginException {
        String id = loginUser.getUserId();
        User user;
        try {
            user = userRepository.findById(id);
        } catch (NoSuchUserException noUser) {
            throw new CanNotLoginException("아이디가 존재하지 않습니다");
        }

        if (!user.isSamePassword(loginUser.getPassword())) {
            throw new CanNotLoginException("비밀번호가 일치하지 않습니다");
        }

        return new UserIdentity(user.getUserId(), user.getName());
    }

    @Override
    public void updateUser(User after) {
        userRepository.updateUser(after);
    }

    @Override
    public boolean checkValueIsDuplicate(String key, String value) {
        if (key.contentEquals("userId")) {
            return !userRepository.isIdExist(value);
        } else return !userRepository.isNameExist(value);
    }
}
