package codesquad.springcafe.users.service;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.users.model.data.UserCredentialData;
import codesquad.springcafe.users.model.dto.*;
import codesquad.springcafe.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreationRequest userCreationRequest) {
        User user = new User(userCreationRequest.getUserId(), userCreationRequest.getEmail(), userCreationRequest.getName(), userCreationRequest.getPassword());
        logger.debug("User Created : {}", user);

        userRepository.createUser(user);
    }

    public ArrayList<UserPreviewDto> getAllUsers() {
        Optional<ArrayList<UserPreviewDto>> articles = userRepository.getAllUsers();
        return articles.orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
    }

    public UserPreviewDto findUserById(String userId) {
        return userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
    }

    public void updateUser(String userId, UserUpdateRequest updateData) {
        UserCredentialData userCredentialData = userRepository.getUserCredential(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        validatePassword(updateData.getCurrentPassword(), userCredentialData);

        userRepository.updateUser(userId, updateData);

        logger.debug("User Updated : {}", userId);
    }

    public UserPreviewDto loginUser(UserLoginRequest userLoginRequest) {
        UserCredentialData userCredentialData = userRepository.getUserCredential(userLoginRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        validatePassword(userLoginRequest.getPassword(), userCredentialData);

        return userRepository.findUserById(userLoginRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private void validatePassword(String inputPassword, UserCredentialData userCredentialData) {
        UserCredentialData inputCredentialData = new UserCredentialData(hashPassword(inputPassword));
        if (!inputCredentialData.equals(userCredentialData)) {
            throw new PasswordMismatchException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }

    private String hashPassword(String password) {
        try {
            // SHA-256 알고리즘을 사용하는 MessageDigest 객체 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 입력 문자열을 바이트 배열로 변환하여 해시 함수에 전달
            byte[] hash = digest.digest(password.getBytes());

            // 해시된 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 16진수 해시 문자열 반환
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 지정된 알고리즘이 없는 경우 예외 처리
            e.printStackTrace();
            return null;
        }
    }
}
