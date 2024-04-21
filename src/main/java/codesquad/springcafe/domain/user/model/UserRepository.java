package codesquad.springcafe.domain.user.model;

import codesquad.springcafe.global.repository.BasicRepository;

import java.util.Optional;

public interface UserRepository extends BasicRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);

    Boolean existsById(Long userId);

    void update(Long userId, User updateUser);
}
