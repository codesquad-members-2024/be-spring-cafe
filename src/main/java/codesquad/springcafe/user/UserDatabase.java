package codesquad.springcafe.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class UserDatabase {

    private final List<User> userDatabase = new ArrayList<>();


    public void save(User user) {
        userDatabase.add(user);
    }

    public List<UserRequestDto> findAll() {
        List<UserRequestDto> userRequestDtoList = new ArrayList<>();
        for (User user : userDatabase) {
            userRequestDtoList.add(
                new UserRequestDto(user.getUserId(), user.getEmail(), user.getNickname()));
        }
        return userRequestDtoList;
    }

    public User findByUserId(String userId) {
        for (User user : userDatabase) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public void clear() {
        userDatabase.clear();
    }


}
