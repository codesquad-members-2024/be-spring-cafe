package springcafe.user.repository;

import org.springframework.stereotype.Repository;
import springcafe.user.model.User;

import java.util.ArrayList;
import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository {

    static List<User> userList = new ArrayList<>();

    @Override
    public void add(User user) {
        userList.add(user);
    }

    @Override
    public User findById(String id) {
        return userList.stream().filter(i -> i.isIdMatched(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 회원이 없습니다."));
    }

    @Override
    public List<User> findAll() {
        return userList;
    }
}
