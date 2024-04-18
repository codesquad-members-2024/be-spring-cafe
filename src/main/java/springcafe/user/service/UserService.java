package springcafe.user.service;

import org.springframework.stereotype.Service;
import springcafe.user.dto.UserDto;
import springcafe.user.model.User;
import springcafe.user.repository.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(String id, String password, String username, String email) {
        User user = new User(id, password, username, email);
        userDao.insert(user);

        return user;
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public List<UserDto> findAll() {
        List<User> userList = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserDto.toUserDto(user));
        }

        return userDtoList;
    }

    public void updateInfo(String id, String password, String name, String email){
        User user = userDao.findById(id);
        user.updatePassword(password);
        user.updateName(name);
        user.updateEmail(email);
    }

    public boolean loginCheck(String id, String password) {
        User user = userDao.findById(id);
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
