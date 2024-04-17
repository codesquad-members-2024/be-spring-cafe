package codesquad.springcafe.user.database;

import codesquad.springcafe.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<User> findAll(){
        return new ArrayList<>(users);
    }

    @Override
    public void save(User user){
        if(user.getId() == null){
            user.setId(nextId++);
        }
        users.add(user);
    }

    @Override
    public User findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateUser(User user, String userId) {
        User findUser = findByUserId(userId);
        findUser.setPassword(user.getPassword());
        findUser.setName(user.getName());
        findUser.setEmail(user.getEmail());
    }


}
