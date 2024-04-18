package codesquad.springcafe.database.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UpdatedUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("MemoryUserDatabase")
public class MemoryUserDatabase implements UserDatabase{
    private final List<User> users = new ArrayList<>();

    @Override
    public void saveUser(User user) {
        users.add(user);
    }

    @Override
    public void updateUser(String id, UpdatedUser updatedUser) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.update(updatedUser);
        } else {
            throw new IllegalArgumentException("User id: " + id + " not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public int getUsersSize(){
        return users.size();
    }

}
