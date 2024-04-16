package codesquad.springcafe.users.repository;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserPreviewDto;
import codesquad.springcafe.model.user.dto.UserUpdateData;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<ArrayList<UserPreviewDto>> getAllUsers();

    Optional<UserPreviewDto> findUserById(String userId);

    Optional<UserCredentialDto> getUserCredential(String userId);

    void updateUser(String userId, UserUpdateData updateData);

}
