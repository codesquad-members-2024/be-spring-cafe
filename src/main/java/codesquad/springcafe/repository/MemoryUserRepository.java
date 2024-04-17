package codesquad.springcafe.repository;

import codesquad.springcafe.DB.Database;
import codesquad.springcafe.domain.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Database database;

    public MemoryUserRepository(Database database) {
        this.database = database;
    }

    @Override
    public void add(User user) {
        database.addUser(user);
    }

    @Override
    public List<User> getAll() {
        return database.getAllUsers();
    }

    @Override
    public User getById(String userId) {
        return database.getUser(userId);
    }

    @Override
    public void update(User user) {
        database.updateUser(user);
    }
}
