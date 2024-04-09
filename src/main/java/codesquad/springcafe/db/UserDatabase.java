package codesquad.springcafe.db;

import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class UserDatabase {

    private final List<User> userDatabase = new ArrayList<>();


    public void save(User user) {
        userDatabase.add(user);
    }

    public List<User> findAll() {
        return userDatabase;
    }


}
