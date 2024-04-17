package codesquad.springcafe.db;

import codesquad.springcafe.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MemoryUserDatabase implements UserDatabase {
    private final List<User> userDatabase = new ArrayList<>();

    public void addUser(User user) {
        userDatabase.add(user);
    }

    @Override
    public void update(String userId, User updateUser) {
        IntStream.range(0, userDatabase.size())
                .filter(i -> userDatabase.get(i).getUserId().equals(userId))
                .findFirst()
                .ifPresent(i -> userDatabase.set(i, updateUser));
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
