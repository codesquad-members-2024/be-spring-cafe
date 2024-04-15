package codesquad.springcafe.user.service;

import codesquad.springcafe.database.Database;
import codesquad.springcafe.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final Database database;

    public UserServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void storeUser(User user) {
        database.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return database.getUsers();
    }
}
