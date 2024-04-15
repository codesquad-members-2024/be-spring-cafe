package codesquad.springcafe.user;

import java.util.List;

public interface UserDatabase {

    void save(User user);

    void update(User user, String userId);

    User findByUserId(String userId);

    List<UserRequestDto> findAll();

    void clear();

}
