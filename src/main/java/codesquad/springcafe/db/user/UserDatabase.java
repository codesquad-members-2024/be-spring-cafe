package codesquad.springcafe.db.user;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;

import java.util.List;
import java.util.Optional;

public interface UserDatabase {
    public void addUser(User user);

    public void update(String userId, UserProfileEditDto userProfileEditDto);

    public List<UserProfileDto> getAllUsers();

    public Optional<UserProfileDto> findUserByUserId(String userId);

    public Optional<UserCredentialDto> getUserCredential(String userId);

    public void clearDatabase();

    public int getTotalUserNumber();

    public boolean existsByUserId(String userId);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);
}
