package codesquad.springcafe.domain.db;

import codesquad.springcafe.domain.User;
import java.util.List;

public interface UserDatabase {
    public void addUser(User user);

    public List<User> getAllUsers();

    public User getUser(String userId);

    public void updateUser(User user);
}
