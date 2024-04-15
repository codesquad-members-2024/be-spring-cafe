package codesquad.springcafe.db;

import codesquad.springcafe.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDatabase {
    private static final List<User> userDatabase = new ArrayList<>();

    private UserDatabase(){}

    public static void addUser(User user) {
        userDatabase.add(user);
    }

    public static List<User> findAllUsers(){
        return userDatabase;
    }

    public static Optional<User> findUserByUserId(String userId){
        return userDatabase.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    public static void clearDatabase(){
        userDatabase.clear();
    }
}
