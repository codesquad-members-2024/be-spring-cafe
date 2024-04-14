package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.data.UserLoginData;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import codesquad.springcafe.global.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static codesquad.springcafe.global.utils.DateUtils.convertCreatedAt;

/**
 * UserRepository와 통신하며 회원 관련 비즈니스 로직을 구현하는 클래스
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO: 예외 클래스 생성해 처리
    // 회원가입
    public void join(UserJoinData userJoinData) {
        // 같은 이메일로 가입한 회원 조회
        userRepository.findByEmail(userJoinData.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 사용자입니다.");
                });
        // 회원 저장
        User user = userJoinData.toUser(
                passwordEncoder.encode(userJoinData.getPassword())  // 비밀번호 암호화
        );
        userRepository.save(user);
    }

    // 로그인
    public Long login(UserLoginData userLoginData) {
        // 회원 존재 여부 확인
        User user = userRepository.findByEmail(userLoginData.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 비밀번호 비교 TODO: 예외 클래스 생성, ExceptionHandler에서 처리
        if (!passwordEncoder.matches(userLoginData.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 아이디 반환
        return user.getId();
    }

    // 회원 목록 조회
    public UserListData getUsers() {
        List<UserData> users = userRepository.findAll().stream()
                .map(u -> new UserData(u.getId(), u.getEmail(), u.getName(),    // id, email, name, createAt만 매핑
                        convertCreatedAt(u.getCreatedAt())))
                .toList();

        // UserData 목록을 UserListData에 담아 반환
        return new UserListData(users);
    }

    // 회원 상세 조회
    public UserData getUser(Long userId) {
        // id로 회원 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        return new UserData(user.getId(), user.getEmail(), user.getName(), convertCreatedAt(user.getCreatedAt()));
    }

}
