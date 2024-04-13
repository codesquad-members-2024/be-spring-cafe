package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.UserRepository;


public class UserService {
    private final UserRepository userRepository = new UserRepository();

    /**
     * 회원 가입
     */
    public String validateDuplicateUser(User user) {
        // 같은 이름이 있는 중복 회원 X
        userRepository.findUserById(user.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        userRepository.addUser(user);
        return user.getUserId();
    }
}
