package codesquad.springcafe.user.respository;

import codesquad.springcafe.database.firstcollection.Users;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static final Users userDB = new Users();


    public void storeUser(User user) {
        userDB.put(user);
    }

    public List<User> getAllUsers() {
        return userDB.getUsers();
    }

    public User findByName(String name) throws NoSuchUserException{
        Optional<User> user = userDB.getUser(name);
        if (user.isEmpty()) throw new NoSuchUserException();

        return user.get();
    }
}
