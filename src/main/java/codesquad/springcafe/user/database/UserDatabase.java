package codesquad.springcafe.user.database;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserRequestDto;
import java.util.List;

public interface UserDatabase {

    void save(User user);

    void update(User user, String userId);

    User findByUserId(String userId);

    List<UserRequestDto> findAll();

    void clear();

}
