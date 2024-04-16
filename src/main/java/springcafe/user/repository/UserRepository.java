package springcafe.user.repository;

import springcafe.user.model.User;

import java.util.List;

public interface UserRepository {
    public void add(User user);
    public User findById(String id);
    public List<User> findAll();
}
