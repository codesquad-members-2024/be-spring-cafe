package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UpdateUser;
import codesquad.springcafe.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 회원가입
    // e) 중복되는 유저는 가입 불가
    public void addNewUser(User user) {
        userRepository.add(user);
    }

    // 모든 유저 반환
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    // 유저 찾기
    // e) 해당되는 유저가 없으면 에러
    public User getUserById(String userId) {
        return userRepository.getById(userId);
    }

    // 유저정보 수정
    // e) 해당되는 유저가 없으면 에러
    public boolean editUserProfile(UpdateUser updateUser, String userId) {
        User target = userRepository.getById(userId);
        String password = updateUser.getPassword();

        if (target.checkPassword(password)) {
            target.setPassword(updateUser.getNewPassword());
            target.setName(updateUser.getName());
            target.setEmail(updateUser.getEmail());
            userRepository.update(target);
            return true;
        }
        return false;
    }
}
