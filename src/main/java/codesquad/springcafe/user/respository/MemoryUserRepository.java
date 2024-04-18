package codesquad.springcafe.user.respository;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static final Map<String, User> userDB = new ConcurrentHashMap<>();


    @Override
    public void storeUser(User user) {
        userDB.put(user.getName(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userDB.values());
    }

    @Override
    public User findByName(String name) throws NoSuchUserException{
        User user = userDB.get(name);
        if (user == null) throw new NoSuchUserException();

        return user;
    }

    @Override
    public User findById(String id) throws NoSuchUserException {
        Optional<User> optionalUser =
                userDB.values().stream()
                        .filter(user -> user.getUserId().equals(id))
                        .findFirst();
        if (optionalUser.isEmpty()) throw new NoSuchUserException();

        return optionalUser.get();
    }
}
