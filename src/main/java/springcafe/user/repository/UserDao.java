package springcafe.user.repository;

import springcafe.user.dto.UserDto;
import springcafe.user.model.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);
    public User findById(String id);
    public List<User> findAll();
}
