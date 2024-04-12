package codesquad.springcafe.domain.user.model;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 기본 repository 로직을 담고 있는 BasicRepository를 상속하는 UserRepository를 구현합니다.
 * <p>UserRepository에는 BasicRepository에 없는 추가 로직 메서드를 정의할 수 있습니다.
 * <p>DB가 아닌 Map에 회원 정보를 저장합니다.
 */
@Repository
public class UserRepositoryLocal implements UserRepository{
    private static final Map<Long, User> users = new HashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    public User save(User user){
        long userId = sequence.incrementAndGet();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByEmail(String email) {
        List<User> findUsers = users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .toList();

        // TODO : 예외 클래스 생성
        if (findUsers.size() > 1) {
            throw new RuntimeException("사용자를 한 명만 조회해야 하지만, 두 명 이상이 조회되고 있습니다.");
        }

        return findUsers.isEmpty() ? Optional.empty() : Optional.ofNullable(findUsers.get(0));
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public Long countAll() {
        return sequence.get();
    }

    public void deleteAll() {
        users.clear();
    }
}
