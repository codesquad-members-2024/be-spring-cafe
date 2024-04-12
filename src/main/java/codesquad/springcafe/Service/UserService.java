package codesquad.springcafe.Service;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; //스프링 컨테이너에 등록

@Service //component scan
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; //구현체 주입
    }

    public User signUp(User user) {
        //중복 회원 체크
        validateDuplicateUser(user);

        userRepository.save(user);
        return user;
    }

    public User update(User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() -> new NoSuchElementException());
        return userRepository.update(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getUserId())
            .ifPresent(result -> {
                throw new IllegalStateException("이미 유저가 존재함");
            });
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}