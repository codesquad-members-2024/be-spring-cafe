package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    public List<User> findAll(){
        return new ArrayList<>(users);
    }

    public void save(User user){
        if(user.getId() == null){
            user.setId(nextId++);
        }
        users.add(user);
    }

    public User findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
