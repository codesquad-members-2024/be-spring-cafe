package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.data.*;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import codesquad.springcafe.global.security.PasswordEncoder;
import codesquad.springcafe.global.utils.DateUtils;
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
    public Long join(UserJoinRequest userJoinRequest) {
        // 같은 아이디로 가입한 회원 조회
        userRepository.findByLoginId(userJoinRequest.getLoginId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 사용자입니다.");
                });
        // 회원 저장
        User user = userJoinRequest.toUser(
                passwordEncoder.encode(userJoinRequest.getPassword())  // 비밀번호 암호화
        );
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    // 로그인
    public Long login(UserLoginRequest userLoginRequest) {
        // 회원 존재 여부 확인
        User user = findUserByLoginId(userLoginRequest.getLoginId());

        // 비밀번호 비교 TODO: 예외 클래스 생성, ExceptionHandler에서 처리
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 아이디 반환
        return user.getId();
    }

    // 로그아웃
    public boolean logout(String requestUserId, Object sessionUserId) {
        if(sessionUserId == null || requestUserId == null) return false;

        Long ruid = Long.parseLong(requestUserId);
        Long suid = (Long) sessionUserId;

        return ruid.equals(suid) && userRepository.existsById(suid);
    }

    // 회원 목록 조회
    public UserListResponse getUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getLoginId(), u.getEmail(), u.getName(),    // loginId, email, name, createAt만 매핑
                        convertCreatedAt(u.getCreatedAt())))
                .toList();

        // UserData 목록을 UserListData에 담아 반환
        return new UserListResponse(users);
    }

    // 회원 상세 조회
    public UserResponse getUser(String loginId) {
        // loginId로 회원 조회
        User user = findUserByLoginId(loginId);
        return new UserResponse(user.getLoginId(), user.getEmail(), user.getName(), convertCreatedAt(user.getCreatedAt()));
    }

    // 내 프로필 조회
    public UserResponse getMyProfile(Long userId) {
        User user = findUserById(userId);
        return new UserResponse(user.getLoginId(), user.getEmail(), user.getName(), DateUtils.convertCreatedAt(user.getCreatedAt()));
    }

    // 내 프로필 수정 TODO : 테스트 가능하도록 수정
    public void updateMyProfile(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = findUserById(userId);
        user.update(userUpdateRequest.getName(), userUpdateRequest.getEmail());
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
    }
}
