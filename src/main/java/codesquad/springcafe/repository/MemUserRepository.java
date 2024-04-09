package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemUserRepository implements UserRepository{

    Map<String , User> users = new ConcurrentHashMap<>();
    @Override
    public void addUser(User user) throws IllegalArgumentException{
        if(users.containsKey(user.getId())) throw new IllegalArgumentException();
        users.put(user.getId() , user);
    }

    @Override
    public User findUserById(String id) {
        return users.get(id);
    }

    @Override
    public List<User> findAll() {
        System.out.println(users.values().size());
        return new ArrayList<>(users.values());
    }
}
