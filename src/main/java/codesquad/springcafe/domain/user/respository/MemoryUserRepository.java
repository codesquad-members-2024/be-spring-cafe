package codesquad.springcafe.domain.user.respository;

import codesquad.springcafe.domain.user.dto.User;
import codesquad.springcafe.exceptions.NoSuchUserException;
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

        return optionalUser.orElseThrow(() -> new NoSuchUserException());
    }

    @Override
    public void removeUser(String name) {
        userDB.remove(name);
    }

    @Override
    public boolean isIdExist(String value) {
        return userDB.values().stream()
                            .anyMatch(user -> user.getUserId().equals(value));
    }

    @Override
    public boolean isNameExist(String value) {
        return userDB.values().stream()
                .anyMatch(user -> user.getName().equals(value));
    }

    @Override
    public void updateUser(User user) {
        userDB.replace(user.getUserId(), user);
    }
}
