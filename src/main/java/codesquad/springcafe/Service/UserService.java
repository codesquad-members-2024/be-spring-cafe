package codesquad.springcafe.Service;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Domain.UserCredential;
import codesquad.springcafe.Repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        userRepository.findById(user.getUserId())
            .ifPresent(result -> {
                throw new IllegalStateException("이미 유저가 존재함");
            });

        userRepository.create(user);
        return user;
    }

    public void update(User updatedUser) {
        userRepository.findById(updatedUser.getUserId())
            .filter(user -> user.getPassword().equals(updatedUser.getPassword()))
            .ifPresentOrElse(
                user -> userRepository.update(updatedUser),
                () -> {
                    throw new NoSuchElementException("User not found or passwords do not match");
                }
            );
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }

    public String login(String email, String password) {
        return userRepository.findByEmail(email).filter(user -> user.getPassword().equals(password))
            .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email)).getUserId();
    }
}