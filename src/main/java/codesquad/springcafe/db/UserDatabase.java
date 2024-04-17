package codesquad.springcafe.db;

import codesquad.springcafe.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public interface UserDatabase {
    public void addUser(User user);

    public void update(String userId, User updatedUser);

    public List<User> findAllUsers();

    public Optional<User> findUserByUserId(String userId);

    public void clearDatabase();

    public int getTotalUserNumber();
}
