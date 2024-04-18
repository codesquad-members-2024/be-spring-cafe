package codesquad.springcafe.users.repository;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.users.model.data.UserCredentialData;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.users.model.dto.UserUpdateDto;
import codesquad.springcafe.users.model.dto.UserUpdateRequest;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<ArrayList<UserPreviewDto>> getAllUsers();

    Optional<UserPreviewDto> findUserById(String userId);

    Optional<UserCredentialData> getUserCredential(String userId);

    void updateUser(String userId, UserUpdateDto updateDto);

}
