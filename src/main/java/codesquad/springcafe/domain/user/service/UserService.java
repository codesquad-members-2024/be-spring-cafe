package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User user = userJoinData.toUser();
        userRepository.save(user);
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

    // LocalDateTime을 날짜 포맷 문자열로 변환하는 메서드
    private String convertCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
