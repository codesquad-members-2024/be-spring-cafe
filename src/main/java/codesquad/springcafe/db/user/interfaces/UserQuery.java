package codesquad.springcafe.db.user.interfaces;

import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;

import java.util.List;
import java.util.Optional;

public interface UserQuery {
    public List<UserProfileDto> getAllUsers();

    public Optional<UserProfileDto> findUserByUserId(String userId);

    public Optional<UserCredentialDto> getUserCredential(String userId);

    public int getTotalUserNumber();
}
