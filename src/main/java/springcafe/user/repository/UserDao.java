package springcafe.user.repository;

import springcafe.user.model.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);
    public User findById(String id);
    public List<User> findAll();
    public void update(User user);
    public boolean checkDuplicateId(String userId);
}
