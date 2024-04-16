package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public Optional<User> findUserById(String userId) {
        // userId에 해당하는 사용자를 찾아서 Optional로 감싸서 반환
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty(); // 해당 userId를 가진 사용자가 없을 경우 empty Optional을 반환
    }

    public List<User> findAll() {
        return users;
    }
}
