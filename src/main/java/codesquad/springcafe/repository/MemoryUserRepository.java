package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> users;

    public MemoryUserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void userUpdate(User updatedUser) {
        // 업데이트할 사용자 정보를 가져옴
        Optional<User> optionalUser = findUserById(updatedUser.getUserId());
        optionalUser.ifPresent(user -> {
            // 업데이트할 사용자 정보를 입력받은 정보로 갱신
            user.setName(updatedUser.getName());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
        });
    }
}
