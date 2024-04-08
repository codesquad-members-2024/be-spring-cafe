package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemoryDatabase implements UserDatabase {
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private long sid = 0L;

    @Override
    public void save(User user) {
        String joinDate = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setJoinDate(joinDate);
        user.setSid(++sid);
        store.put(sid, user);
    }

    @Override
    public User findByUserId(String userId) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {

    }
}
