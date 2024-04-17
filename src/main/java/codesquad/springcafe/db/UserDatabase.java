package codesquad.springcafe.db;

import codesquad.springcafe.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDatabase {
    private final List<User> userDatabase = new ArrayList<>();

    public void addUser(User user) {
        userDatabase.add(user);
    }

    public List<User> findAllUsers(){
        return userDatabase;
    }

    public Optional<User> findUserByUserId(String userId){
        return userDatabase.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    public void clearDatabase(){
        userDatabase.clear();
    }

    public int getTotalUserNumber(){
        return userDatabase.size();
    }
}
