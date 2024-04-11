package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.repository.MemoryUserRepository;

import java.util.List;

public class UserService {
    private MemoryUserRepository repository = new MemoryUserRepository();

    public void join(User user) {
        validateDuplicateUser(user);
        repository.save(user);
    }

    private void validateDuplicateUser(User user) {
        repository.findById(user.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> findUsers() {
        return repository.findAll();
    }

}
