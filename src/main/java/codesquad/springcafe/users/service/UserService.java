package codesquad.springcafe.users.service;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.users.model.data.UserCredentialData;
import codesquad.springcafe.users.model.dto.*;
import codesquad.springcafe.users.repository.UserRepository;
import codesquad.springcafe.utils.SHA256HashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final SHA256HashService hashService;

    @Autowired
    public UserService(UserRepository userRepository, SHA256HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }

    public void createUser(UserCreationRequest userCreationRequest) {
        String hashedPassword = hashService.hashPassword(userCreationRequest.getPassword());
        User user = new User(userCreationRequest.getUserId(), userCreationRequest.getEmail(), userCreationRequest.getName(), hashedPassword);
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

    public void updateUser(String userId, UserUpdateRequest updateRequest) {
        // updateRequest의 currentPassword를 sha-256 해시
        String hashedCurrentPassword = hashService.hashPassword(updateRequest.getCurrentPassword());

        // 비밀번호 검증을 위한 객체 생성
        UserCredentialData inputCredentialData = new UserCredentialData(hashedCurrentPassword);
        UserCredentialData userCredentialData = userRepository.getUserCredential(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 객체 간의 비밀번호 값 검증
        validatePassword(inputCredentialData, userCredentialData);

        // Repository로 보내기 위한 dto 생성
        UserUpdateDto updateDto = new UserUpdateDto(updateRequest.getNewName(), updateRequest.getNewEmail(), hashService.hashPassword(updateRequest.getNewPassword()));

        userRepository.updateUser(userId, updateDto);

        logger.debug("User Updated : {}", userId);
    }

    public UserPreviewDto loginUser(UserLoginRequest userLoginRequest) {
        UserCredentialData inputCredentialData = new UserCredentialData(hashService.hashPassword(userLoginRequest.getPassword()));
        UserCredentialData userCredentialData = userRepository.getUserCredential(userLoginRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        validatePassword(inputCredentialData, userCredentialData);

        return userRepository.findUserById(userLoginRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private void validatePassword(UserCredentialData inputCredentialData, UserCredentialData userCredentialData) {
        if (!inputCredentialData.equals(userCredentialData)) {
            throw new PasswordMismatchException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }


}
