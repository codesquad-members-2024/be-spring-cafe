package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User createUser(UserDto userDto);

    User updateUser(String userId, UserUpdateDto userUpdateDto);

    List<User> findAllUsers();

    Optional<User> findUserByUserId(String userId);
}
