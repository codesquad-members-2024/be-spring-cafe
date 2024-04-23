package springcafe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import springcafe.user.dto.ChageInfoForm;
import springcafe.user.login.security.PasswordHashing;
import springcafe.user.model.User;
import springcafe.user.repository.UserDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final PasswordHashing passwordHashing;
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, PasswordHashing passwordHashing) {
        this.userDao = userDao;
        this.passwordHashing = passwordHashing;
    }

    public User create(String id, String password, String username, String email) {
        String hashPassword = passwordHashing.hashPassword(password);
        User user = new User(id, hashPassword, username, email);
        userDao.insert(user);

        return user;
    }

    public List<User> findAll() {
        List<User> userList = userDao.findAll();
        List<User> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(user);
        }

        return userDtoList;
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public boolean checkHashPassword(ChageInfoForm chageInfoForm, String hashPasswordFromDb) {
        String hashInputPassword = passwordHashing.hashPassword(chageInfoForm.getPassword());

        return hashInputPassword.equals(hashPasswordFromDb);
    }


    public void updateInfo(String id, String name, String email) {
        User user = userDao.findById(id);
        user.updateName(name);
        user.updateEmail(email);

        userDao.update(user);
    }

    public boolean checkUserIdExists(@PathVariable String userId) {

       return userDao.checkDuplicateId(userId);
    }

}
