package codesquad.springcafe.service;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import java.util.List;

public interface UserService {
    User addUser(User user);

    User findUserByUserId(String userId) throws Exception;

    User updateUser(String userId, UpdatedUser updatedUser) throws Exception;

    User deleteUser(User user);

    List<User> findAllUser();
}
