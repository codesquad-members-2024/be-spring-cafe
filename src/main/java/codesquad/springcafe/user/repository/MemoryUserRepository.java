package codesquad.springcafe.user.repository;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository{

    private final Map<String , User> users = new ConcurrentHashMap<>();

    public MemoryUserRepository(){
        addUser(new User("tester" , "1234" , "테스터", "test@naver.com"));
    }
    @Override
    public void addUser(User user) throws IllegalArgumentException{
        if(users.containsKey(user.getUserId())) throw new IllegalArgumentException();
        users.put(user.getUserId() , user);
    }

    @Override
    public void update(User user) {
        this.users.replace(user.getUserId() , user);
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
            users.add(new UserDTO(i+1 , user.getUserId() , user.getName(), user.getEmail()));
        }

        return users;
    }
}
