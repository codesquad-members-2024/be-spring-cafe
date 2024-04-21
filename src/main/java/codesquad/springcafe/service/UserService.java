package codesquad.springcafe.service;

import codesquad.springcafe.dto.user.LoginDTO;
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
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDTO signUp(SignUpDTO signUpDTO) {
        User newUser = signUpDTO.toUser();
        userRepository.save(newUser);
        return newUser.toDTO();
    }

    public List<UserInfoDTO> findAll() {
        List<User> users = userRepository.getAll();
        return LongStream.rangeClosed(1, users.size())
            .mapToObj(index -> users.get((int)index - 1).toDTO(index))
            .collect(Collectors.toList());
    }

    public UserInfoDTO findById(String userId) {
        Optional<User> targetUser = userRepository.getById(userId);
        return targetUser.map(User::toDTO).orElse(null);
    }

    public UserInfoDTO updateInfo(String userId, UserUpdateDTO updateInfo) {
        User modifiedUser = updateInfo.toUser(userId);
        userRepository.modify(modifiedUser);
        return modifiedUser.toDTO();
    }

    public Optional<UserInfoDTO> login(LoginDTO loginDTO) {
        Optional<User> targetUser = userRepository.getById(loginDTO.getUserId());

        if (targetUser.isPresent() && targetUser.get().isPasswordCorrect(loginDTO.getPassword())) {
            return Optional.of(targetUser.get().toDTO());
        }
        return Optional.empty();
    }
}