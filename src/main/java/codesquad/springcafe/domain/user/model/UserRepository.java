package codesquad.springcafe.domain.user.model;

import codesquad.springcafe.global.repository.BasicRepository;

import java.util.Optional;

public interface UserRepository extends BasicRepository<User, Long> {

    Optional<User> findById(Long userId, Boolean deleted);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId, Boolean deleted);

    Boolean existsById(Long userId);

    void update(Long userId, User updateUser);
}
