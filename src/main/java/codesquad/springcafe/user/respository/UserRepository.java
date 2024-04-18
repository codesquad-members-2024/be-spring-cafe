package codesquad.springcafe.user.respository;

import codesquad.springcafe.database.Database;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final Database database;

    @Autowired
    public UserRepository(Database database) {
        this.database = database;
    }

    public void storeUser(User user) {
        database.addUser(user);
    }

    public List<User> getAllUsers() {
        return database.getUsersAsList();
    }

    public User findByName(String name) throws NoSuchUserException{
        return database.findUserByName(name);
    }
}
