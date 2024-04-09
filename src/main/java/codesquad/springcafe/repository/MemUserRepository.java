package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

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
    public List<UserDTO> findAll() {
        List<User> values = new ArrayList<>(users.values());
        List<UserDTO> users = new ArrayList<>();

        for(int i=0; i<values.size(); i++){
            User user = values.get(i);
            users.add(new UserDTO(i+1 , user.getId() , user.getName(), user.getEmail()));
        }

        return users;
    }
}
