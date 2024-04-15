package codesquad.springcafe.database;

import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.database.firstcollection.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemoryDatabase implements Database{

    private static final Users userDatabase = new Users();

    @Override
    public void addUser(User user) {
        userDatabase.put(user);
    }

    @Override
    public List<User> getUsers() {
        return userDatabase.getUsers();
    }
}
