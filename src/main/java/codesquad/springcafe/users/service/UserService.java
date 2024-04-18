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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        UserCredentialData inputCredentialData = new UserCredentialData(hashService.hashPassword(updateData.getCurrentPassword()));
        UserCredentialData userCredentialData = userRepository.getUserCredential(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        validatePassword(inputCredentialData, userCredentialData);

        userRepository.updateUser(userId, updateData);

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
