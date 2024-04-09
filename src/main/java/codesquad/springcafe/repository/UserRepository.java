package codesquad.springcafe.repository;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import java.util.List;

public interface UserRepository {
    User addUser(User user);

    User findUserByUserID(String userId) throws Exception;

    User updateUser(String userId, UpdatedUser updatedUser) throws Exception;

    User deleteUser(User user);

    List<User> findAllUser();
}
