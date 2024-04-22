package codesquad.springcafe.db.user;

import codesquad.springcafe.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDatabase {
    public void addUser(User user);

    public void update(String userId, User updatedUser);

    public List<User> findAllUsers();

    public Optional<User> findUserByUserId(String userId);

    public void clearDatabase();

    public int getTotalUserNumber();
}
