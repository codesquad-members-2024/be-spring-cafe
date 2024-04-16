package codesquad.springcafe.users.repository;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.users.model.dto.UserCredentialDto;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.users.model.dto.UserUpdateData;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<ArrayList<UserPreviewDto>> getAllUsers();

    Optional<UserPreviewDto> findUserById(String userId);

    Optional<UserCredentialDto> getUserCredential(String userId);

    void updateUser(String userId, UserUpdateData updateData);

}
