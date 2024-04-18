package codesquad.springcafe.users.repository;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.db.UserDatabase;
import codesquad.springcafe.users.model.data.UserCredentialData;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.users.model.dto.UserUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);

    private final UserDatabase userDatabase;

    @Autowired
    public MemoryUserRepository(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public void createUser(User user) {
        userDatabase.addUser(user);
    }

    @Override
    public Optional<ArrayList<UserPreviewDto>> getAllUsers() {
        ArrayList<User> users = userDatabase.getAllUsers();
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
        User user = userDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        UserPreviewDto userPreviewDto = new UserPreviewDto(user.getUserId(), user.getName(), user.getEmail(), user.getCreationDate().toString());

        return Optional.of(userPreviewDto);
    }

    @Override
    public Optional<UserCredentialData> getUserCredential(String userId) {
        User user = userDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

        UserCredentialData userCredentialData = new UserCredentialData(user.getSalt(), user.getHashedPassword());

        return Optional.of(userCredentialData);
    }


    @Override
    public void updateUser(String userId, UserUpdateRequest updateData) {
        User user = userDatabase.findUserById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));

        user.updateUser(updateData);

        logger.debug("User Updated : {}", user);
    }


}
