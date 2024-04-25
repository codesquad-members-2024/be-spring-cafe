package codesquad.springcafe.repository;

import codesquad.springcafe.domain.db.UserDatabase;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final UserDatabase database;

    public MemoryUserRepository(UserDatabase database) {
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
    public Optional<User> getById(String userId) {
        return Optional.of(database.getUser(userId));
    }

    @Override
    public void update(User user) {
        database.updateUser(user);
    }
}
