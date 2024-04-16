package codesquad.springcafe.users.repository;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.db.UserDatabase;
import codesquad.springcafe.users.model.dto.UserCredentialDto;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.users.model.dto.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);

    @Override
    public void createUser(User user) {
        UserDatabase.addUser(user);
    }

    @Override
    public Optional<ArrayList<UserPreviewDto>> getAllUsers() {
        ArrayList<User> users = UserDatabase.getAllUsers();
        ArrayList<UserPreviewDto> userPreviews = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserPreviewDto userPreviewDto = new UserPreviewDto(user.getUserId(), user.getName(), user.getEmail(), user.getCreationDate().toString());
            userPreviews.add(userPreviewDto);
        }
        return Optional.of(userPreviews);
    }

    @Override
    public Optional<UserPreviewDto> findUserById(String userId) {
        User user = UserDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        UserPreviewDto userPreviewDto = new UserPreviewDto(user.getUserId(), user.getName(), user.getEmail(), user.getCreationDate().toString());

        return Optional.of(userPreviewDto);
    }

    @Override
    public Optional<UserCredentialDto> getUserCredential(String userId) {
        User user = UserDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

        UserCredentialDto userCredentialDto = new UserCredentialDto(user.getPassword());

        return Optional.of(userCredentialDto);
    }


    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        User user = UserDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));

        user.updateUser(updateData);

        logger.debug("User Updated : {}", user);
    }


}
