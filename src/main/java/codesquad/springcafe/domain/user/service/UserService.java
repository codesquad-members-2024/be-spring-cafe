package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinData userJoinData) {
        userRepository.findByEmail(userJoinData.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 사용자입니다.");
                });
        User user = userJoinData.toUser();
        userRepository.save(user);
    }

    public UserListData getUsers() {
        List<UserData> users = userRepository.findAll().stream()
                .map(u -> new UserData(u.getEmail(), u.getName()))
                .toList();

        return new UserListData(users);
    }

}
