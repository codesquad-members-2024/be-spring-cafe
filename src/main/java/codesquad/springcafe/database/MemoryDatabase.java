package codesquad.springcafe.database;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.database.firstcollection.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemoryDatabase implements Database{

    private static final Users userDatabase = new Users();

    @Override
    public void addUser(User user) {
        userDatabase.put(user);
    }

    @Override
    public List<User> getUsersAsList() {
        return userDatabase.getUsers();
    }

    @Override
    public User findUserByName(String name) throws NoSuchUserException{
        Optional<User> user = userDatabase.getUser(name);
        if (user.isEmpty()) {
            throw new NoSuchUserException();
        }

        return user.get();
    }
}
