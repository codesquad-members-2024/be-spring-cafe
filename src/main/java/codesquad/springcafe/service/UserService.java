package codesquad.springcafe.service;

import codesquad.springcafe.dto.user.SignUpDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.dto.user.UserUpdateDTO;
import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfoDTO signUp(SignUpDTO signUpDTO) {
        User newUser = signUpDTO.toUser(passwordEncoder);
        userRepository.save(newUser);
        return newUser.toDTO();
    }

    public List<UserInfoDTO> findAllUsers() {
        List<User> users = userRepository.getAll();
        return LongStream.rangeClosed(1, users.size())
            .mapToObj(index -> users.get((int)index - 1).toDTO(index))
            .collect(Collectors.toList());
    }

    public UserInfoDTO findUserById(String userId) {
        Optional<User> targetUser = userRepository.getById(userId);
        if (targetUser.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return targetUser.get().toDTO();
    }

    public UserInfoDTO updateInfo(String userId, UserUpdateDTO updateInfo) {
        User modifiedUser = updateInfo.toUser(userId, passwordEncoder);
        userRepository.modify(modifiedUser);
        return modifiedUser.toDTO();
    }
}