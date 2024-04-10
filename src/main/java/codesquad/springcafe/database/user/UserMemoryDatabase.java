package codesquad.springcafe.database.user;

import codesquad.springcafe.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserMemoryDatabase implements UserDatabase {
    private final List<User> store = new ArrayList<>();

    @Override
    public User add(User user) {
        user.setJoinDate(LocalDate.now());
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findBy(String nickname) {
        return store.stream()
                .filter(user -> user.hasSameNickname(nickname))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }
}
