package codesquad.springcafe.domain.user.repository;

import codesquad.springcafe.annotation.TestRepository;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.DTO.UserListRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TestRepository
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
    public List<UserListRes> findAll() {
        List<User> values = new ArrayList<>(users.values());
        List<UserListRes> users = new ArrayList<>();

        for(int i=0; i<values.size(); i++){
            User user = values.get(i);
            users.add(new UserListRes(i+1 , user.getUserId() , user.getName(), user.getEmail()));
        }

        return users;
    }
}
